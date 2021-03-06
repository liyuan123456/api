package com.li.api.enums;

import java.util.stream.Stream;

/**
 * @author 黎源
 * @date 2021/1/19 12:47
 */
public enum OrderStatus {
    All(0, "全部"),
    UNPAID(1, "待支付"),
    PAID(2, "已支付"),
    DELIVERED(3, "已发货"),
    FINISHED(4, "已完成"),
    CANCELED(5, "已取消"),

    // 预扣除库存不存在以下这两种情况
    PAID_BUT_OUT_OF(21, "已支付，但无货或库存不足"),
    DEAL_OUT_OF(22, "已处理缺货但支付的情况");

    private int value;
    private String text;

    OrderStatus(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int value() {
        return this.value;
    }

    public String text() {
        return this.text;
    }

    public static OrderStatus toType(int value) {
        return Stream.of(OrderStatus.values())
                .filter(c -> c.value == value)
                .findAny()
                .orElse(null);
    }
}
