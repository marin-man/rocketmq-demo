package com.manman.base.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * @Title: OneWayProducer
 * @Author manman
 * @Description 发送单向消息
 * @Date 2021/9/13
 */
public class OneWayProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("group3");
        producer.setNamesrvAddr("192.168.10.102:9876;192.168.10.103:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message msg = new Message("base", "Tag3", ("hello world，单向消息" + i).getBytes());
            // 发送单向消息
            producer.sendOneway(msg);
            TimeUnit.SECONDS.sleep(1);
        }
        producer.shutdown();
    }
}
