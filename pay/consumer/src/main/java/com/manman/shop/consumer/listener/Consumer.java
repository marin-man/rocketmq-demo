package com.manman.shop.consumer.listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Title: Consumer
 * @Author manman
 * @Description 消费者监听器
 * @Date 2021/9/19
 */
@RocketMQMessageListener(topic = "springboot-rocketmq", consumerGroup = "${rocketmq.consumer.group}")
@Component
public class Consumer implements RocketMQListener<String> {

    public void onMessage(String s) {
        System.out.println("接收到消息：" + s);
    }
}
