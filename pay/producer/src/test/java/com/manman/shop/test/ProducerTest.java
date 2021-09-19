package com.manman.shop.test;

import com.manman.shop.ProducerApplication;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Title: ProducerTest
 * @Author manman
 * @Description
 * @Date 2021/9/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProducerApplication.class})
public class ProducerTest {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void testSendMessage() {
        rocketMQTemplate.convertAndSend("springboot-rocketmq", "hello Springboot RocketMQ");
    }
}
