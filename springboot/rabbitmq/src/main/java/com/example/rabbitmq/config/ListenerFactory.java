package com.example.rabbitmq.config;

import com.example.rabbitmq.consumer.RabbitMqExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.rabbitmq.config.RabbitmqConfig.FAIL_QUEUE_NAME;
import static com.example.rabbitmq.config.RabbitmqConfig.TOPIC_EXCHANGE_NAME;


@RequiredArgsConstructor
@Configuration
public class ListenerFactory {


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /*
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());

        return rabbitTemplate;
    }
    */

    @Bean("listenerContainerFactory")
    public SimpleRabbitListenerContainerFactory listenerContainerFactory(ConnectionFactory connectionFactory){

        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setDefaultRequeueRejected(false);
        factory.setMessageConverter(messageConverter());
        factory.setChannelTransacted(true);
        factory.setAdviceChain(RetryInterceptorBuilder
                .stateless()
                .maxAttempts(1)
                //.recoverer(new RepublishMessageRecoverer(rabbitTemplate, TOPIC_EXCHANGE_NAME, FAIL_QUEUE_NAME))
                .recoverer(new RabbitMqExceptionHandler())
                .backOffOptions(1000, 2.0, 10000)
                .build());

        return factory;
    }
}
