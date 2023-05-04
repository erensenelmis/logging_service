package com.eren.logger.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "logger.implementation=mq")
public class ConditionalServiceMQTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testMQService() {
        applicationContext.getBean(RabbitMQLoggingService.class);
    }

    @Test
    void testKafkaService() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(KafkaLoggingService.class));
    }

    @Test
    void testFileService() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(FileLoggingService.class));
    }
}
