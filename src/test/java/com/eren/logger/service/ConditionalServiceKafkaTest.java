package com.eren.logger.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "logger.implementation=kafka")
public class ConditionalServiceKafkaTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testKafkaService() {
        // No exception
        applicationContext.getBean(KafkaLoggingService.class);
    }

    @Test
    void testMQService() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(RabbitMQLoggingService.class));
    }

    @Test
    void testFileService() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(FileLoggingService.class));
    }
}
