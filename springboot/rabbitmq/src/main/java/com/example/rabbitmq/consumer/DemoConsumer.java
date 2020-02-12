package com.example.rabbitmq.consumer;

import com.example.rabbitmq.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.example.rabbitmq.config.RabbitmqConfig.QUEUE_NAME;

@Component
@Slf4j
public class DemoConsumer {

    //@RabbitListener(queues = QUEUE_NAME)
    public void messageConsumer1(Message receivedMessage){
        log.info("메세지 수신. " + receivedMessage);
    }

    @RabbitListener(queues = QUEUE_NAME)
    public void messageConsumer2(Item item){
        //log.info("아이템 수신. from 2" + item);


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(item.isThrow()){
            log.info("에러 던짐2222");
            throw new AmqpRejectAndDontRequeueException("error!!!");
        }else {
            log.info("아이템 수신. from 2" + item);
        }

    }

    //@RabbitListener(queues = QUEUE_NAME)
    public void messageConsumer3(Item item){
        log.info("아이템 수신. from 3" + item);
    }
}
