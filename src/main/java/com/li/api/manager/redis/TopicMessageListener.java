package com.li.api.manager.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * @author 黎源
 * @date 2021/1/25 17:13
 */
public class TopicMessageListener implements MessageListener {


    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();

        String expiredKey = new String(body);
        String topic = new String(channel);

        System.out.println(expiredKey);
        System.out.println(topic);
    }
}
