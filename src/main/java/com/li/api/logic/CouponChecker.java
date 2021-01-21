package com.li.api.logic;

import com.li.api.enums.CouponType;
import com.li.api.exception.ForbiddenException;
import com.li.api.exception.ParamterException;
import com.li.api.money.HalfEvenDiscout;
import com.li.api.pojo.bo.SkuOrderBO;
import com.li.api.pojo.model.Category;
import com.li.api.pojo.model.Coupon;
import com.li.api.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 黎源
 * @date 2021/1/11 17:47
 */
public class CouponChecker {
    private Coupon coupon;

    public CouponChecker(Coupon coupon) {
        this.coupon = coupon;
    }

    /**
     * 验证优惠券是否过期
     *
     * @return
     */
    public void isOK() {
        Boolean isExpired = DateUtil.isExpired(new Date(), coupon.getStartTime(), coupon.getEndTime());
        if (!isExpired) {
            throw new ForbiddenException(40001);
        }
    }

    /**
     * 价格校验
     *
     * @param orderFinalTotalPrice
     * @param serverTotalPrice
     */
    public void finalTotalPriceIsOK(BigDecimal orderFinalTotalPrice,
                                    BigDecimal serverTotalPrice) {
        BigDecimal serverFinalTotalPrice;
        switch (CouponType.toType(coupon.getType())) {
            case FULL_MINUS:
            case NO_THRESHOLD_MINUS:
                serverFinalTotalPrice = serverTotalPrice.subtract(coupon.getMinus());
                //订单金额不能小于等于 0
                if (serverFinalTotalPrice.compareTo(new BigDecimal("0")) < 1) {
                    throw new ForbiddenException(50002);
                }
                break;
            case FULL_OFF:
                serverFinalTotalPrice = new HalfEvenDiscout()
                        .mathDiscount(serverTotalPrice, coupon.getRate());
                break;

            default:
                throw new ParamterException(10001);
        }
        int compare = orderFinalTotalPrice.compareTo(serverFinalTotalPrice);
        if (compare != 0) {
            throw new ForbiddenException(50002);
        }
    }

    /**
     * 优惠券能否使用校验 主方法
     *
     * @param skuOrderBOS
     * @param serverTotalPrice
     */
    public void canBeUsed(List<SkuOrderBO> skuOrderBOS, BigDecimal serverTotalPrice) {
        BigDecimal orderCategoryPrice;
        if (coupon.getWholeStore()) {
            orderCategoryPrice = serverTotalPrice;
        } else {
            List<Long> cids = coupon.getCategoryList().stream()
                    .map(Category::getId)
                    .collect(Collectors.toList());
            orderCategoryPrice = getSumByCategoryList(skuOrderBOS, cids);
        }
        couponCanBeUsed(orderCategoryPrice);
    }

    /**
     * 判断各类型优惠券是否达到门槛
     *
     * @param orderCategoryPrice
     */
    private void couponCanBeUsed(BigDecimal orderCategoryPrice) {
        switch (CouponType.toType(coupon.getType())) {
            case FULL_OFF:
            case FULL_MINUS:
                int compare = coupon.getFullMoney().compareTo(orderCategoryPrice);
                if (compare > 0) {
                    throw new ParamterException(50002);
                }
                break;
            case NO_THRESHOLD_MINUS:
                break;
            default:
                throw new ParamterException(50001);
        }
    }

    /**
     * 所属优惠券所有分类sku总价
     *
     * @param skuOrderBOS
     * @param cids
     * @return
     */
    private BigDecimal getSumByCategoryList(List<SkuOrderBO> skuOrderBOS, List<Long> cids) {
        return cids.stream()
                .map(cid -> getSumByCategory(skuOrderBOS, cid))
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
    }

    /**
     * 所属优惠券某一分类sku总价
     *
     * @param skuOrderBOS
     * @param cid
     * @return
     */
    private BigDecimal getSumByCategory(List<SkuOrderBO> skuOrderBOS, Long cid) {
        return skuOrderBOS.stream()
                .filter(skuOrderBO -> skuOrderBO.getCategoryId().equals(cid))
                .map(SkuOrderBO::getTotalPrice)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
    }


}
