package com.example.demo;

import com.example.model.entity.Demo;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author banana
 * @create 2024-07-13 19:17
 */
@SpringBootTest
public class MqProcessTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Test
    public void sendmessageTest() {

        // 定义发送消息内容
        String msg = "this is a msg test!";

        Demo demo = new Demo();
        demo.setSex("男");
        demo.setName("yjy");
        demo.setContext("这是一个消息");
        demo.setId(1);

        /**
         * public void convertAndSend(String exchange, String routingKey, Object
         object)
         * -exchange：交换机
         * -routingKey： 路由键
         * -object： 消息内容对象（Object类型）
         * 注意：发送消息的时候，如果消息是个对象，我们会使用序列化机制，将对象发送出去
         * 并且，对象必须实现爱你Serializable
         */
        rabbitTemplate.convertAndSend("banana-direct-exchange", "banana-common", demo);
        System.out.println("消息发送成！");
    }


}
