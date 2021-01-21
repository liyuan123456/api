package com.li.api.pojo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 黎源
 * @date 2021/1/11 14:52
 */
@Setter
@Getter
public class OrderDto {

    @DecimalMin(value = "0.00",message = "不在合法范围内")
    @DecimalMax(value = "99999999.99", message = "不在合法范围内")
    private BigDecimal totalPrice;
    @DecimalMin(value = "0.00",message = "不在合法范围内")
    @DecimalMax(value = "99999999.99", message = "不在合法范围内")
    private BigDecimal finalTotalPrice;
    private Long couponId;
    private List<SkuInfoDto> skuInfoDtoList;
    private OrderAddressDto address;
}
