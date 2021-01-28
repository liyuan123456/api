package com.li.api.manager.redis;

import com.li.api.pojo.bo.OrderMessageBo;
import com.li.api.service.CouponBackImpl;
import com.li.api.service.OrderCancelImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * @author 黎源
 * @date 2021/1/25 17:13
 */

public class TopicMessageListener implements MessageListener {

    @Autowired
    private OrderCancelImpl orderCancel;

    @Autowired
    private CouponBackImpl couponBack;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();

        String expiredKey = new String(body);
        String topic = new String(channel);

        OrderMessageBo orderMessageBo = new OrderMessageBo(expiredKey);
        orderCancel.cancel(orderMessageBo);
        couponBack.backCoupon(orderMessageBo);
    }
}
