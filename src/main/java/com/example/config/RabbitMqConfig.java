package com.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.annotation.PostConstruct;

/**
 * @author banana
 * @create 2024-07-13 19:22
 */
/**
 * @author banana
 * @create 2024-07-13 19:22
 */
@Slf4j
@Configuration
public class RabbitMqConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 设置确认回调方法一
     * 在当前配置类完成属性注入后配置
     */
    @PostConstruct
    public void initRabbitTemplate(){

        rabbitTemplate.setMandatory(true);

        // 设置确认回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             * 只要消息抵达Broker就ack=true
             * @param correlationData 当前消息的唯一关联数据（这个消息的唯一id）
             * @param ack 消息是否成功收到
             * @param cause 失败的原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                    log.info("消息成功发送到exchange");
                } else {
                    log.error("消息发送exchange失败:" + cause);
                }

            }
        });

        // 设置消息抵达队列的消息回调
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            /**
             * 只要消息没有投递给指定的队列，就触发这个失败回调
             * @param returnedMessage 封装了上买的参数
             */
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                System.out.println("message:" + returnedMessage.getMessage());
                System.out.println("replyCode:" + returnedMessage.getReplyCode());
                System.out.println("replyText:" + returnedMessage.getReplyText());
                System.out.println("exchange:" + returnedMessage.getExchange());
                System.out.println("routingKey:" + returnedMessage.getRoutingKey());
            }
        });
    }

}

@Configuration
class MyMessageConverterConfig {
    /**
     * 配置mq消息转换器为jackson
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

