package com.example.rabbitmq.producer;

import com.example.rabbitmq.Item;
import com.example.rabbitmq.config.RabbitmqConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import static com.example.rabbitmq.config.RabbitmqConfig.TOPIC_EXCHANGE_NAME;

@Component
@AllArgsConstructor
@Slf4j
public class ProductTrigger {

    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "1 * * * * *")
    public void schedule() {

        IntConsumer converter = makeConverter(false);

        IntStream.range(1, 5)
                .parallel()
                .forEach(converter);
    }

    private IntConsumer makeConverter(boolean isThrow){
        return (num)-> {
            //rabbitTemplate.convertAndSend
            //        (TOPIC_EXCHANGE_NAME, "foo.bar.baz", "message"+num);


            Item item = Item.builder().data("data" + num).number(num).isThrow(isThrow).build();

            rabbitTemplate.convertAndSend
                    (TOPIC_EXCHANGE_NAME, "foo.bar.baz2", item);
        };
    }
}
