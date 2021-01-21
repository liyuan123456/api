package com.li.api.pojo.vo;

import com.li.api.pojo.model.Coupon;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 黎源
 * @date 2021/1/8 20:15
 */
@Setter
@Getter
public class CouponCategoryVo extends CouponSimpleVo {
    private List<CategoryVo> categoryVos;

    public CouponCategoryVo(Coupon coupon) {
        BeanUtils.copyProperties(coupon, this);
        this.categoryVos = coupon.getCategoryList()
                .stream()
                .map(CategoryVo::new)
                .collect(Collectors.toList());
    }
}
