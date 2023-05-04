package com.eren.logger;

import com.eren.logger.controller.LoggingController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoggingServiceApplicationTests {

	@Autowired
	private LoggingController controller;

	@Value(value="${local.server.port}")
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testContextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	void testServerIsUp() {
		assertThat(restTemplate.getForObject("http://localhost:" + port + "/status", String.class))
				.isEqualTo("OK");
	}
}
