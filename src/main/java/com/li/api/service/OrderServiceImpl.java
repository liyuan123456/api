package com.li.api.service;

import com.li.api.core.UserLocal;
import com.li.api.enums.OrderStatus;
import com.li.api.exception.ForbiddenException;
import com.li.api.exception.NotFoundException;
import com.li.api.exception.ParamterException;
import com.li.api.logic.CouponChecker;
import com.li.api.logic.OrderChecker;
import com.li.api.pojo.dto.OrderDto;
import com.li.api.pojo.dto.SkuInfoDto;
import com.li.api.pojo.model.*;
import com.li.api.repository.CouponRepository;
import com.li.api.repository.OrderRepository;
import com.li.api.repository.SkuRepository;
import com.li.api.repository.UserCouponRepository;
import com.li.api.util.CommonUtil;
import com.li.api.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 黎源
 * @date 2021/1/11 15:59
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SkuServiceImpl skuService;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private UserCouponRepository userCouponRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SkuRepository skuRepository;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Value("${conventional.max-buy}")
    private int maxBuy;
    @Value("${conventional.pay-time-limit}")
    private int payTimeLimit;
    /**
     * 下单
     *
     * @param uid
     * @param orderDto
     * @param orderChecker
     * @return
     */
    @Transactional
    public Long placeOrder(Long uid, OrderDto orderDto, OrderChecker orderChecker) {
        Order order = Order.builder()
                .orderNo(OrderUtil.makeOrderNo())
                .totalCount(orderChecker.getTotalCount().longValue())
                .finalTotalPrice(orderDto.getFinalTotalPrice())
                .userId(uid)
                .totalPrice(orderDto.getTotalPrice())
                .snapImg(orderChecker.getLeaderImg())
                .snapTitle(orderChecker.getLeaderTitle())
                .status(OrderStatus.UNPAID.value())
                .placedTime(new Date())
                .expiredTime(CommonUtil.addSomeSeconds(Calendar.getInstance(), payTimeLimit).getTime())
                .build();
        order.setSnapAddress(orderDto.getAddress());
        order.setSnapItems(orderChecker.getOrderSkus());
        orderRepository.save(order);
        // 库存预扣除
        reduceStock(orderChecker);
        // 核销优惠券
        Long couponId = -1L;
        if (orderDto.getCouponId() != null) {
            writeOffCoupon(uid, order.getId(), orderDto.getCouponId());
            couponId = orderDto.getCouponId();
        }
        //加入延迟消息队列
        sendToRedis(order.getId(),uid,couponId);
        return order.getId();
    }

    /**
     * 校验
     *
     * @param uid
     * @param orderDto
     * @return
     */
    public OrderChecker isOK(Long uid, OrderDto orderDto) {
        List<Long> ids = orderDto.getSkuInfoDtoList().stream()
                .map(SkuInfoDto::getId)
                .collect(Collectors.toList());
        List<Sku> skus = skuService.getSkusByIdS(ids);
        CouponChecker checker = null;
        // 进行order校验 优惠券校验
        Long couponId = orderDto.getCouponId();
        if (couponId != null) {
            Coupon coupon = couponRepository.findById(couponId)
                    .orElseThrow(() -> new NotFoundException(30006));
            UserCoupon userCoupon = userCouponRepository.findFirstByUserIdAndCouponId(uid, couponId)
                    .orElseThrow(() -> new NotFoundException(30007));
            checker = new CouponChecker(coupon);
        }
        OrderChecker orderChecker = new OrderChecker(orderDto, skus, checker, maxBuy);
        orderChecker.isOK();
        return orderChecker;
    }

    /**
     * 扣除库存
     *
     * @param orderChecker
     */
    private void reduceStock(OrderChecker orderChecker) {
        List<OrderSku> orderSkus = orderChecker.getOrderSkus();
        for (OrderSku skus : orderSkus) {
            int result = skuRepository.reduceStock(skus.getId(), skus.getCount().longValue());
            if (result != 1) {
                throw new ParamterException(50005);
            }
        }
    }

    /**
     * 核销优惠券
     *
     * @param uid
     * @param oid
     * @param couponId
     */
    private void writeOffCoupon(Long uid, Long oid, Long couponId) {
        int result = userCouponRepository.writeOff(uid, oid, couponId);
        if (result != 1) {
            throw new ParamterException(30007);
        }
    }

    /**
     * 获取待支付 已取消订单
     * @param page
     * @param size
     * @return
     */
    public Page<Order> getUnpaid(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        Long uid = UserLocal.getUser().getId();
        Page<Order> orderPage = orderRepository.findByExpiredTimeGreaterThanAndStatusAndUserId(new Date(),
                OrderStatus.UNPAID.value(),
                uid,
                pageable);
        if (orderPage.getContent().isEmpty()) {
            throw new NotFoundException(30009);
        }
        return orderPage;
    }


    /**
     * 获取不同状态订单
     * @param page
     * @param size
     * @param status
     * @return
     */
    public Page<Order> getByStatus(Integer page, Integer size,Integer status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        Long uid = UserLocal.getUser().getId();
        Page<Order> orderPage = Page.empty();
        if (OrderStatus.UNPAID.value() == status || OrderStatus.CANCELED.value() == status) {
            throw new ParamterException(10001);
        }
        if (OrderStatus.All.value() == status) {
            orderPage = orderRepository.findAllByUserId(uid, pageable);
        }else {
            orderPage = orderRepository.findByUserIdAndStatus(uid, status, pageable);
        }
        if (orderPage.getContent().isEmpty()) {
            throw new NotFoundException(30009);
        }
        return orderPage;
    }

    /**
     * 获取订单详情
     * @param oid
     * @return
     */
    public Order getOrderDetail(Long oid) {
        Long uid = UserLocal.getUser().getId();
        Optional<Order> orderOptional = orderRepository.findByUserIdAndId(uid, oid);
        return orderOptional.orElseThrow(() -> new NotFoundException(30009));
    }

    /**
     * 写入redis 设置过期时间
     * @param oid
     * @param uid
     * @param couponId
     */
    private void sendToRedis(Long oid,Long uid,Long couponId) {
        String key = oid.toString() + "," + uid.toString() + "," + couponId.toString();
        try {
            stringRedisTemplate.opsForValue().set(key, "1", payTimeLimit, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
