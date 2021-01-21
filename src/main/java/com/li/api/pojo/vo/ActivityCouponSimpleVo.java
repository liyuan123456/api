package com.li.api.pojo.vo;

import com.li.api.pojo.model.Activity;
import com.li.api.pojo.model.Coupon;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 黎源
 * @date 2020/12/30 20:08
 */
@Getter
@Setter
public class ActivityCouponSimpleVo extends ActivitySimpleVo {
    private List<CouponSimpleVo> coupons;

    public ActivityCouponSimpleVo(Activity activity) {
        super(activity);
        coupons = activity.getCouponList()
                .stream().map(CouponSimpleVo::new)
                .collect(Collectors.toList());
    }
}
