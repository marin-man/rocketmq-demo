package com.manman.base.consumer;

        import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
        import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
        import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
        import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
        import org.apache.rocketmq.client.exception.MQClientException;
        import org.apache.rocketmq.common.message.MessageExt;
        import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

        import java.util.List;

/**
 * @Title: Consumer1
 * @Author manman
 * @Description 负载均衡接收消息
 * @Date 2021/9/17
 */
public class Consumer1 {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        consumer.setNamesrvAddr("192.168.10.102:9876;192.168.10.103:9876");
        consumer.subscribe("base", "Tag2");
        // 设置消费模式，负载均衡 | 广播，默认负载均衡
        consumer.setMessageModel(MessageModel.CLUSTERING); // 开启负载均衡
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt ext : list) {
                    System.out.println(ext);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}
