package com.example.listener;

import com.example.model.entity.Demo;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author banana
 * @create 2024-07-13 20:39
 */
@Component
public class MqListener {

    /**
     * @RabbitListener参数：
     * - queues： 声明监听的队列
     *
     *
     *
     * 入参说明：
     * 1. Message message 原生消息内容。头+体
     * 2. T<发送消息的类型> 会帮我们自动转化放到该对象中
     * 3. Channel channel: 当前传输数据的通道
     */
    @RabbitListener(queues = {"banana-common-queue"})
    public void recieveMessage(Message message, Demo content, Channel channel){
        System.out.println("接收到队列中的消息……" + message);
        System.out.println("接收到队列中的消息类型" + message.getClass());

        // RabbitMQ 消息的投递标签（delivery tag）
        // 每个投递给消费者的消息都有一个唯一的投递标签，它用来标识消息在通道中的唯一位置
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        System.out.println("deliveryTag:" + deliveryTag);

        // 拒绝货物(并重新入队，会重新推给其他消费者进行消费)
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
