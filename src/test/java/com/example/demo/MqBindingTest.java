package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author banana
 * @create 2024-07-13 18:30
 */
@SpringBootTest
public class MqBindingTest {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    public void creatBinding(){
        /**
         * public Binding(String destination, Binding.DestinationType
         destinationType, String exchange, String routingKey, Map<String, Object>
         arguments) {
         * this.destination = destination;
         * this.destinationType = destinationType;
         * this.exchange = exchange;
         * this.routingKey = routingKey;
         * this.arguments = arguments;
         * }
         * - destination: 目的地
         * - destinationType: 目的地类型（交换机、队列）
         * - exchange: 交换机
         * - routingKey: 路由键
         * - arguments: 自定义采纳数
         * 将exchange指定的交换机和destination目的地进行绑定，使用routingKey作为指定路由键
         */
        Binding binding = new Binding("banana-common-queue",
                Binding.DestinationType.QUEUE,
                "banana-direct-exchange",
                "banana-common", null);
        amqpAdmin.declareBinding(binding);
        System.out.println("binding创建成功");
    }

}
