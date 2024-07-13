package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author banana
 * @create 2024-07-13 18:23
 */
@SpringBootTest
public class MqExchangeTest {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    void contextLoads() {
    }


    /**
     * 创建交换机
     */
    @Test
    public void createDirectExchange() {
        /*
         * 创建一个名称为 banana-direct-exchange交换机(类型为：direct)
         * public DirectExchange(String name, boolean durable, boolean autoDelete, Map<String, Object> arguments)
         * - name: 交换机名称
         * - durable: 交换器是否可持久化
         * - autoDelete: 交换机是否在没有队列绑定的情况下自动删除
         * - arguments: 传递的一些参数
         */

        System.out.println("direct交换机开始创建……");

        DirectExchange directExchange = new DirectExchange("banana-direct-exchange", true, false);

        amqpAdmin.declareExchange(directExchange);

        System.out.println("direct交换机创建成功……");

    }
}
