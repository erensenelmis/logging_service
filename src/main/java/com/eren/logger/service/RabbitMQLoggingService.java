package com.eren.logger.service;

import com.eren.logger.model.LogRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "logger", name = "implementation", havingValue = "mq")
public class RabbitMQLoggingService implements LoggingService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void push(LogRequest request) throws LogDestinationException {
        try {
            rabbitTemplate.convertAndSend(RabbitMQQueueConfig.QUEUE_NAME, objectMapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            throw new LogDestinationException(e);
        }
    }
}
