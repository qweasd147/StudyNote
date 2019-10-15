package com.example.rabbitmq.consumer;

import com.example.rabbitmq.Item;
import com.example.rabbitmq.config.RabbitmqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.example.rabbitmq.config.RabbitmqConfig.QUEUE_NAME;

@Component
@Slf4j
public class DemoConsumer {

    @RabbitListener(queues = QUEUE_NAME)
    public void messageConsumer1(Message receivedMessage){
        log.info("메세지 수신. " + receivedMessage);
    }

    @RabbitListener(queues = QUEUE_NAME)
    public void messageConsumer2(Item item){
        log.info("아이템 수신. " + item);
    }
}
