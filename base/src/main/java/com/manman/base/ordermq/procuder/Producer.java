package com.manman.base.ordermq.procuder;

import com.manman.base.ordermq.order.OrderStep;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

/**
 * @Title: Producer
 * @Author manman
 * @Description 消息生产
 * @Date 2021/9/18
 */
public class Producer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("192.168.10.102:9876;192.168.10.103:9876");
        producer.start();
        List<OrderStep> orderSteps = OrderStep.buildOrders();
        for (int i = 0; i < orderSteps.size(); i++) {
            String body = orderSteps.get(i) + "";
            Message message = new Message("OrderTopic", "Order", "i" + i, body  .getBytes());
            // 参数一：消息对象，参数二：消息队列的选择器，参数三：选择队列的业务标识（订单ID）
            SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                // 参数一：队列集合，参数二：消息对象，参数三：业务标识的参数
                public MessageQueue select(List<MessageQueue> list, Message message, Object arg) {
                    Long orderId = (Long) arg;
                    int index = (int) (orderId % list.size());
                    return list.get(index);
                }
            }, orderSteps.get(i).getOrderId());
            System.out.println("发送结果：" + sendResult);
        }
        producer.shutdown();
    }
}
