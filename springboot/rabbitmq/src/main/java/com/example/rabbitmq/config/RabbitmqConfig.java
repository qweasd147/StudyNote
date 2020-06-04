package com.example.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class RabbitmqConfig {

    public static final String QUEUE_NAME = "demo-queue-name";
    public static final String TOPIC_EXCHANGE_NAME = QUEUE_NAME+"-exchange";

    public static final String DLX_EXCHANGE_NAME = "fail-" + QUEUE_NAME+"-exchange";
    public static final String DLX_QUEUE_NAME = "demo-expired-queue-name";
    public static final String DLX_ROUTING_KEY = "foo.fail.key";

    @Bean
    public Queue queue() {

        Map<String, Object> args = new HashMap<>();

        args.put("x-dead-letter-exchange", RabbitmqConfig.DLX_EXCHANGE_NAME);
        args.put("x-dead-letter-routing-key", "foo.fail.key");
        args.put("x-message-ttl", 2000); //2초

        //return new Queue(QUEUE_NAME);
        return new Queue(QUEUE_NAME, true, false, false, args);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(@Qualifier("queue")Queue queue, @Qualifier("exchange")TopicExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }

    /*
    dlx 관련 broker는 어노테이션 기반으로 구현
    @Bean
    public Binding bindingFail(@Qualifier("expiredQueue")Queue queue, @Qualifier("failExchange")DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.fail.key");
    }

    @Bean
    public Queue expiredQueue() {
        return new Queue(DLX_QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange failExchange() {
        return new DirectExchange(DLX_EXCHANGE_NAME);
    }
    */
}