package com.example.controller;


import com.example.model.entity.Demo;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author banana
 * @create 2023-10-06 21:57
 */
@RestController
@RequestMapping("/demo")
public class democontroller {

    @Resource
    private RabbitTemplate rabbitTemplate;


    @GetMapping("/test")
    public void test() {
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
        /*rabbitTemplate.convertAndSend("banana-direct-exchange", "banana-common", demo);*/
        rabbitTemplate.convertAndSend("banana-direct-exchange", "banana-common", demo,
                new CorrelationData(UUID.randomUUID().toString()));
        System.out.println("消息发送成！");
    }
}
