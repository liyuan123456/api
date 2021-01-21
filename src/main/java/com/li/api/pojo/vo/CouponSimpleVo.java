package com.li.api.pojo.vo;

import com.li.api.pojo.model.Coupon;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 黎源
 * @date 2020/12/30 20:05
 */
@Setter
@Getter
public class CouponSimpleVo {
    private Long id;
    private String title;
    private Date startTime;
    private Date endTime;
    private String description;
    private BigDecimal fullMoney;
    private BigDecimal minus;
    private BigDecimal rate;
    private Integer type;
    private String remark;
    private Boolean wholeStore;

    public CouponSimpleVo() {

    }
    public CouponSimpleVo(Coupon coupon) {
        BeanUtils.copyProperties(coupon,this);
    }
}
