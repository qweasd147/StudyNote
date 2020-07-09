package com.example.rabbitmq.consumer;

import com.example.rabbitmq.Item;
import com.example.rabbitmq.config.RabbitmqConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * dlx consumer는 어노테이션 기반으로 작성
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DemoDLXConsumer {

    private final MessageConverter messageConverter;

    @RabbitListener(
        containerFactory = "listenerContainerFactory",
        bindings = @QueueBinding(
            value = @Queue(
                value = RabbitmqConfig.DLX_QUEUE_NAME
                /* dlx는 argument 생략
                , arguments = {
                    @Argument(name = "x-message-ttl", value = "2000", type = "java.lang.Integer"),
                    , @Argument(name = "x-dead-letter-exchange", value = RabbitmqConfig.DLX_EXCHANGE_NAME)
                    , @Argument(name = "x-dead-letter-routing-key", value = RabbitmqConfig.DLX_ROUTING_KEY)
                }
                */
            )
            , exchange = @Exchange(value = RabbitmqConfig.DLX_EXCHANGE_NAME, type = ExchangeTypes.DIRECT)
            , key = RabbitmqConfig.DLX_ROUTING_KEY
        )
    )
    public void failConsumer(Message message){

        Item item = (Item) messageConverter.fromMessage(message);
        Map<String, Object> header = message.getMessageProperties().getHeaders();

        String exchangeName = (String) header.get("x-first-death-exchange");

        List<Map<String, Object>>xDeath = (List)header.get("x-death");
        String firstReason = (String) header.get("x-first-death-reason");

        log.info("타임아웃된 아이템 수신 {}", item);

        log.warn("reason : {}, exchange name : {}", firstReason, exchangeName);
        log.warn("x-death : {}", xDeath.get(0).toString());
    }
}
