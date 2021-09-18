package com.manman.base.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @Title: Producer
 * @Author manman
 * @Description 事务消息生产
 * @Date 2021/9/18
 */
public class Producer {
    public static void main(String[] args) throws MQClientException {
        TransactionMQProducer producer = new TransactionMQProducer("group1");
        producer.setNamesrvAddr("192.168.10.102:9876;192.168.10.103:9876");
        producer.setTransactionListener(new TransactionListener() {
            /**
             * 在该方法中执行本地事务
             * @param message
             * @param o
             * @return
             */
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                if (StringUtils.equals("TAG1", message.getTags())) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else if (StringUtils.equals("TAG2", message.getTags())) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else {
                    return LocalTransactionState.UNKNOW;
                }
            }

            /**
             * MQ 进行消息事务状态的回查
             * @param messageExt
             * @return
             */
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println("消息的 Tag：" + messageExt.getTags());
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        producer.start();

        String[] tags = {"TAG1", "TAG2", "TAG3"};
        for (int i = 0; i < 3; i++) {
            Message msg = new Message("TransactionTopic", tags[i], ("Hello World" + i).getBytes());
            SendResult result = producer.sendMessageInTransaction(msg, null);
            System.out.println(result);
        }
    }
}
