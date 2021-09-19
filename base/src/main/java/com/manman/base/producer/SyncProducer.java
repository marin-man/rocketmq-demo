package com.manman.base.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * @Title: SyncProducer
 * @Author manman
 * @Description 发送同步消息
 * @Date 2021/9/13
 */
public class SyncProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        // 1. 创建消息生产者
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        // 2. 指定 Nameserver 地址
        producer.setNamesrvAddr("192.168.10.102:9876;192.168.10.103:9876");
        // 3. 启动 producer
        producer.start();
        for (int i = 0; i < 10; i++) {
            // 创建消息对象，指定主题 Topic，tag 和消息体
            /**
             * 参数一：Topic
             * 参数二：tag
             * 参数三：消息内容
             */
            Message msg = new Message("base", "Tag1", ("Hello World" + i).getBytes());
            // 5. 发送消息
            SendResult result = producer.send(msg);
            SendStatus status = result.getSendStatus();
            String msgId = result.getMsgId();
            int queueId = result.getMessageQueue().getQueueId();
            System.out.println("发送状态：" + result + ", 消息Id" + msgId + ",队列" + queueId);

            TimeUnit.SECONDS.sleep(1);
        }
        // 6. 关闭生产者
        producer.shutdown();
    }
}
