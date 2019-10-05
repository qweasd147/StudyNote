package com.example.rabbitmq.producer;

import com.example.rabbitmq.config.RabbitmqConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
@Slf4j
public class ProductTrigger {

    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "1 * * * * *")
    public void schedule() {

        IntConsumer converter = makeConverter();

        IntStream.range(1, 100)
                .parallel()
                .forEach(converter);
    }

    private IntConsumer makeConverter(){
        return (num)-> rabbitTemplate.convertAndSend
                (RabbitmqConfig.QUEUE_NAME, "foo.bar.baz", "message"+num);
    }
}
