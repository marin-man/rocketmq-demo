package com.manman.base.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * @Title: AsyncProducer
 * @Author manman
 * @Description 发送异步消息
 * @Date 2021/9/13
 */
public class AsyncProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("group2");
        producer.setNamesrvAddr("192.168.10.102:9876;192.168.10.102:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message("base", "Tag2", ("hello world"+i).getBytes());
            producer.send(message, new SendCallback() {
                /**
                 * 成功回调函数
                 * @param sendResult
                 */
                public void onSuccess(SendResult sendResult) {
                    System.out.println("消息发送成功");
                }

                /**
                 * 失败回调函数
                 * @param e
                 */
                public void onException(Throwable e) {
                    System.out.println("发送异常" + e);
                }
            });
            TimeUnit.SECONDS.sleep(1);
        }
        producer.shutdown();
    }
}
