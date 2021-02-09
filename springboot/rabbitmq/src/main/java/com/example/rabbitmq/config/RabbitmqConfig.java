package com.example.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;



@Configuration
public class RabbitmqConfig {

    public static final String QUEUE_NAME1 = "demo-queue-name1";
    public static final String QUEUE_NAME2 = "demo-queue-name2";
    public static final String TOPIC_EXCHANGE_NAME = QUEUE_NAME1+"-exchange";

    public static final String DLX_EXCHANGE_NAME = "fail-" + QUEUE_NAME1+"-exchange";
    public static final String DLX_QUEUE_NAME = "demo-expired-queue-name";
    public static final String DLX_ROUTING_KEY = "foo.fail.key";


    private static final Map<String, Object> QUEUE_DEFAULT_ARGUMENTS =
            Stream.of(
                new AbstractMap.SimpleEntry<>("x-dead-letter-exchange", RabbitmqConfig.DLX_EXCHANGE_NAME)
                , new AbstractMap.SimpleEntry<>("x-dead-letter-routing-key", "foo.fail.key")
                , new AbstractMap.SimpleEntry<>("x-message-ttl", 2000)  //2초
            ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    @Bean("queue1")
    public Queue queue1() {

        return new Queue(QUEUE_NAME1, true, false, false, QUEUE_DEFAULT_ARGUMENTS);
    }

    @Bean("queue2")
    public Queue queue2() {

        return new Queue(QUEUE_NAME2, true, false, false, QUEUE_DEFAULT_ARGUMENTS);
    }


    @Bean("topicExchange")
    public TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public Binding binding1(@Qualifier("queue1")Queue queue, @Qualifier("topicExchange")TopicExchange topicExchange) {

        return BindingBuilder.bind(queue).to(topicExchange).with("foo.bar.#");
    }

    @Bean
    public Binding binding2(@Qualifier("queue2")Queue queue, @Qualifier("topicExchange")TopicExchange topicExchange) {

        return BindingBuilder.bind(queue).to(topicExchange).with("foo.bar.#");
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