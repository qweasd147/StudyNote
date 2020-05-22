package com.example.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitmqConfig {

    public static final String QUEUE_NAME = "demo-queue-name";
    public static final String TOPIC_EXCHANGE_NAME = QUEUE_NAME+"-exchange";

    public static final String FAIL_QUEUE_NAME = "demo-fail-queue-name";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public Queue failQueue() {
        return new Queue(FAIL_QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(@Qualifier("queue")Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }

    @Bean
    public Binding bindingFail(@Qualifier("failQueue")Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(FAIL_QUEUE_NAME);
    }
}