package com.li.api.manager.recketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author 黎源
 * @date 2021/1/27 17:53
 */
@Component
public class ProducerSchedule {
    private DefaultMQProducer defaultMQProducer;

    @PostConstruct
    public void defaultMqProducter() {
        if (defaultMQProducer == null) {
            defaultMQProducer = new DefaultMQProducer("SimpleDefaultProducter");
            defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");
        }
        try {
            defaultMQProducer.start();
            System.out.println("producter is start");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public String send(String topic, String tag, String message) throws Exception {
        Message mqmessage = new Message(topic,message.getBytes());
        mqmessage.setDelayTimeLevel(3);

        SendResult result = defaultMQProducer.send(mqmessage);
        return result.getMsgId();
    }
}
