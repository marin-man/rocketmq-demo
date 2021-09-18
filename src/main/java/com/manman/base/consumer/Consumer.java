package com.manman.base.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @Title: Consumer
 * @Author manman
 * @Description 消息接收者
 * @Date 2021/9/17
 */
public class Consumer {
    public static void main(String[] args) throws MQClientException {
        // 1. 创建消费者 Consumer。指定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        // 2. 指定 NameServer 地址
        consumer.setNamesrvAddr("192.168.10.102:9876;192.168.10.103:9876");
        // 3. 订阅主题 Topic 和 Tag
        consumer.subscribe("base", "Tag1");
        // 4. 设置回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            // 接收消息内容
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 5. 启动消费者
        consumer.start();
    }
}
