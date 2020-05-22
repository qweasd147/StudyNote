package com.example.rabbitmq;

import com.example.rabbitmq.producer.ProductTrigger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import static com.example.rabbitmq.config.RabbitmqConfig.TOPIC_EXCHANGE_NAME;


@SpringBootApplication
@EnableScheduling
public class DemoApplication implements CommandLineRunner {

    @Autowired
    ProductTrigger productTrigger;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        IntConsumer converter = (num)-> {
            Item item = Item.builder().data("main app" + num).number(num).isThrow(true).build();

            rabbitTemplate.convertAndSend
                    (TOPIC_EXCHANGE_NAME, "foo.bar.baz3", item);
        };

        /*
        IntStream.range(1, 2)
                .parallel()
                .forEach(converter);
        */
    }
}
