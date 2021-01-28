package com.li.api.service;

import com.li.api.enums.OrderStatus;
import com.li.api.exception.ServerException;
import com.li.api.pojo.bo.OrderMessageBo;
import com.li.api.pojo.model.Order;
import com.li.api.repository.OrderRepository;
import com.li.api.repository.UserCouponRepository;
import com.li.api.repository.UserRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author 黎源
 * @date 2021/1/25 19:40
 */
@Service
public class CouponBackImpl {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Transactional(rollbackFor = ServerException.class)
    public void backCoupon(OrderMessageBo orderMessageBo) {
        long uid = orderMessageBo.getUid();
        long couponId = orderMessageBo.getCouponId();
        long orderId = orderMessageBo.getOrderId();
        if (couponId == -1) {
            return;
        }
        Optional<Order> orderOptional = orderRepository.findByUserIdAndId(uid, orderId);
        Order order = orderOptional.orElseThrow(() -> new ServerException(9999));

        if (order.getStatus() == OrderStatus.UNPAID.value() || order.getStatus() == OrderStatus.CANCELED.value()) {
            userCouponRepository.backCoupon(uid, couponId);
        }
    }
}
