package com.eren.logger.service;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "logger", name = "implementation", havingValue = "mq")
public class RabbitMQQueueConfig {

    public static final String QUEUE_NAME = "loggingQueue";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }
}
