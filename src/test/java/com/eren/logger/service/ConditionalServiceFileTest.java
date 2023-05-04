package com.eren.logger.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "logger.implementation=file")
public class ConditionalServiceFileTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testFileService() {
        applicationContext.getBean(FileLoggingService.class);
    }

    @Test
    void testMQService() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(RabbitMQLoggingService.class));
    }

    @Test
    void testKafkaService() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(KafkaLoggingService.class));
    }
}
