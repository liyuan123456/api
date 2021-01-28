package com.li.api.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 黎源
 * @date 2021/1/25 19:37
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderMessageBo {
    private Long orderId;
    private Long uid;
    private Long couponId;
    private String message;

    public OrderMessageBo(String message) {
        this.message = message;
        parseId();
    }

    private void parseId() {
        String[] item = message.split(",");
        orderId = Long.valueOf(item[0]);
        uid = Long.valueOf(item[1]);
        couponId = Long.valueOf(item[2]);
    }
}
