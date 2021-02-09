package com.example.rabbitmq.consumer;

import com.example.rabbitmq.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.example.rabbitmq.config.RabbitmqConfig.*;

@Component
@Slf4j
public class DemoConsumer {


    //@RabbitListener(queues = QUEUE_NAME)
    public void messageConsumer1(Message receivedMessage){
        log.info("메세지 수신. " + receivedMessage);
    }

    @RabbitListener(queues = QUEUE_NAME1, containerFactory = "listenerContainerFactory")
    public void messageConsumer2(Item item){
        //log.info("아이템 수신. from 2" + item);

        log.info("메세지 수신 from queue 1");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(item.isThrow()){
            throw new RuntimeException("Error Data 수신");
            //throw new AmqpRejectAndDontRequeueException("error!!!");
        }
    }

    @RabbitListener(queues = QUEUE_NAME2, containerFactory = "listenerContainerFactory")
    public void messageConsumer4(Item item){
        //log.info("아이템 수신. from 2" + item);

        log.info("메세지 수신 from queue 4");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("강제 에러 발생 from 4");
        /*
        if(item.isThrow()){
            throw new RuntimeException("Error Data 수신");
            //throw new AmqpRejectAndDontRequeueException("error!!!");
        }
        */
    }

    @RabbitListener(queues = QUEUE_NAME1)
    public void messageConsumer3(Item item){
        log.info("아이템 수신. from 3 {}", item);
    }
}
