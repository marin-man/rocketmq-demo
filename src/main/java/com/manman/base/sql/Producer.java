package com.manman.base.sql;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * @Title: Producer
 * @Author manman
 * @Description
 * @Date 2021/9/18
 */
public class Producer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("192.168.10.102:9876;192.168.10.103:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message msg = new Message("FilterSQLTopic", "Tag1", ("hello world" + i).getBytes());
            msg.putUserProperty("i", String.valueOf(i));
            SendResult result = producer.send(msg);
            System.out.println(result);
            TimeUnit.SECONDS.sleep(1);
        }
        producer.shutdown();
    }
}
