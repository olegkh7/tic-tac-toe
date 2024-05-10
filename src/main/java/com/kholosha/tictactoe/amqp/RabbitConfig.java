package com.kholosha.tictactoe.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;


@Configuration
public class RabbitConfig {

    @Value("${message.listenerQueue}")
    private String listenerQueueName;
    @Value("${message.destinationQueue}")
    private String destinationQueueName;
    @Value("${message.retryAttemptsCount}")
    private Integer retryAttemptsCount;
    @Value("${message.retryAttemptsDelayMillis}")
    private Integer retryAttemptsDelayMillis;


    @Bean
    public Queue listenerQueue() {
        return new Queue(listenerQueueName);
    }

    @Bean
    public Queue destinationQueue() {
        return new Queue(destinationQueueName);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setRetryTemplate(retryTemplate());
        return rabbitTemplate;
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(retryAttemptsCount);
        retryTemplate.setRetryPolicy(retryPolicy);
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(retryAttemptsDelayMillis);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }
}
