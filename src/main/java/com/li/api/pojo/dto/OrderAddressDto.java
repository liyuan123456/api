package com.li.api.pojo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 黎源
 * @date 2021/1/11 14:59
 */

@Setter
@Getter
public class OrderAddressDto {
    private String userName;
    private String province;
    private String city;
    private String County;
    private String mobile;
    private String nationalCode;
    private String postalCode;
    private String detail;
}
