package com.example.rabbitmq.consumer;

import com.example.rabbitmq.config.RabbitmqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DemoConsumer {

    @RabbitListener(queues = RabbitmqConfig.QUEUE_NAME)
    public void messageConsumer(Message receivedMessage){
        log.info("메세지 수신. " + receivedMessage);
    }
}
