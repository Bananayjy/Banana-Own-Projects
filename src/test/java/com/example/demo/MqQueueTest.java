package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author banana
 * @create 2024-07-13 18:26
 */
@SpringBootTest
public class MqQueueTest {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    public void createQueue() {
        /**
         * 创建一个队列 banana-common-queue
         * public Queue(String name, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
         * - name： 队列名称
         * - durable： 是否可持久化
         * - exclusive：是否排他（只能被连接一次，当该队列被一个连接后，其他不能再连接）
         * - autoDelete：是否自动删除
         * - arguments：参数
         */
        Queue queue = new Queue("banana-common-queue", true, false, false);
        amqpAdmin.declareQueue(queue);
        System.out.println("queue创建成功");

    }
}
