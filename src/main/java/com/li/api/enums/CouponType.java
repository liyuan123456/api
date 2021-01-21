package com.li.api.enums;

import lombok.Getter;
import lombok.Setter;

import java.util.stream.Stream;

/**
 * @author 黎源
 * @date 2021/1/11 19:49
 */
@Getter
public enum CouponType {
    FULL_MINUS(1, "满减券"),
    FULL_OFF(2, "满减折扣券"),
    NO_THRESHOLD_MINUS(3, "无门槛减除券");

    private Integer value;
    private String description;

    CouponType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static CouponType toType(int value) {
        return Stream.of(CouponType.values())
                .filter(c -> c.getValue() == value)
                .findAny()
                .orElse(null);
    }
}
