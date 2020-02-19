package com.example.rabbitmq.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static com.example.rabbitmq.config.RabbitmqConfig.FAIL_QUEUE_NAME;
import static com.example.rabbitmq.config.RabbitmqConfig.TOPIC_EXCHANGE_NAME;

@Component
@RequiredArgsConstructor
public class ListenerFactory {

    private final RabbitTemplate rabbitTemplate;
    private final MessageConverter messageConverter;


    @Bean
    public SimpleRabbitListenerContainerFactory listenerContainerFactory(ConnectionFactory connectionFactory){

        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setDefaultRequeueRejected(false);
        factory.setMessageConverter(messageConverter);
        factory.setChannelTransacted(true);
        factory.setAdviceChain(RetryInterceptorBuilder
                .stateless()
                .maxAttempts(3)
                .recoverer(new RepublishMessageRecoverer(rabbitTemplate, TOPIC_EXCHANGE_NAME, FAIL_QUEUE_NAME))
                .backOffOptions(1000, 2.0, 10000)
                .build());

        return factory;
    }

    /*
    @Bean
    public RetryTemplate retryTemplate(){
        RetryTemplate retry = new RetryTemplate();
        ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();

        policy.setInitialInterval(3000);    //처음 메세지 처리 실패 시 3초후 다시 메세지 처리


        return retry;
    }
    */
}
