package com.eren.logger.service;

import com.eren.logger.model.LogRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "logger", name = "implementation", havingValue = "kafka")
public class KafkaLoggingService implements LoggingService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void push(LogRequest request) throws LogDestinationException {
        try {
            kafkaTemplate.send(KafkaTopicConfig.TOPIC_NAME, objectMapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            throw new LogDestinationException(e);
        }
    }
}
