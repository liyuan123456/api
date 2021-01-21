package com.li.api.enums;

import java.util.stream.Stream;

/**
 * @author 黎源
 * @date 2021/1/6 16:50
 */
public enum CouponStatus {
    USABLE(1,"可以使用"),
    USED(2,"已经使用"),
    OVERDUE(3,"已过期");

    private Integer value;
    private String msg;

    public Integer getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }

    CouponStatus(Integer value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public static CouponStatus toType(int type) {
        return Stream.of(CouponStatus.values())
                .filter(c -> c.value == type)
                .findAny()
                .orElse(null);
    }
}
