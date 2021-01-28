package com.li.api.manager.recketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author 黎源
 * @date 2021/1/27 21:08
 */
@Component
public class ConsumerSchedule implements CommandLineRunner {
    public void messageListener() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("defaultPushConsumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("mytopic", "*");
        consumer.setConsumeMessageBatchMaxSize(1);
        consumer.registerMessageListener((MessageListenerConcurrently)(messages, context) -> {
            for (MessageExt message : messages) {
                System.out.println(new String(message.getBody()));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
    }

    @Override
    public void run(String... args) throws Exception {
        messageListener();
    }
}
