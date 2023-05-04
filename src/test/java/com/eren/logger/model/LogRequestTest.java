package com.eren.logger.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LogRequestTest {

    ValidatorFactory factory;

    Validator validator;

    @BeforeEach
    void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        factory.close();
    }

    @Test
    void testValidateGivenNullAllFieldsThenInvalid() {
        // given
        LogRequest request = new LogRequest();

        // when
        Set<ConstraintViolation<LogRequest>> violations = validator.validate(request);

        // then
        assertNotNull(getViolation("applicationID", violations));
        assertEquals("{jakarta.validation.constraints.NotNull.message}", getViolation("applicationID", violations).getMessageTemplate());
        assertNotNull(getViolation("traceID", violations));
        assertEquals("{jakarta.validation.constraints.NotNull.message}", getViolation("traceID", violations).getMessageTemplate());
        assertNotNull(getViolation("severity", violations));
        assertEquals("{jakarta.validation.constraints.NotNull.message}", getViolation("severity", violations).getMessageTemplate());
        assertNotNull(getViolation("message", violations));
        assertEquals("{jakarta.validation.constraints.NotNull.message}", getViolation("message", violations).getMessageTemplate());
        assertNull(getViolation("componentName", violations));
        assertNull(getViolation("requestID", violations));
    }

    @Test
    void testValidateGivenAllFieldsValidThenValid() {
        // given
        LogRequest request = new LogRequest("Request-01", "Trace-01", Severity.DEBUG, System.currentTimeMillis(), "This is a valid message");

        // when
        Set<ConstraintViolation<LogRequest>> violations = validator.validate(request);

        // then
        assertNull(getViolation("applicationID", violations));
        assertNull(getViolation("traceID", violations));
        assertNull(getViolation("severity", violations));
        assertNull(getViolation("message", violations));
        assertNull(getViolation("componentName", violations));
        assertNull(getViolation("requestID", violations));
    }

    @Test
    void testValidateGivenFieldLengthLessThanMinThenInvalid() {
        // given
        LogRequest request = new LogRequest("r1", "t1", Severity.DEBUG, System.currentTimeMillis(), "m1", "c1", "r1");

        // when
        Set<ConstraintViolation<LogRequest>> violations = validator.validate(request);

        // then
        assertNotNull(getViolation("applicationID", violations));
        assertEquals("{jakarta.validation.constraints.Size.message}", getViolation("applicationID", violations).getMessageTemplate());
        assertNotNull(getViolation("traceID", violations));
        assertEquals("{jakarta.validation.constraints.Size.message}", getViolation("traceID", violations).getMessageTemplate());
        assertNotNull(getViolation("message", violations));
        assertEquals("{jakarta.validation.constraints.Size.message}", getViolation("message", violations).getMessageTemplate());
        assertNotNull(getViolation("componentName", violations));
        assertEquals("{jakarta.validation.constraints.Size.message}", getViolation("componentName", violations).getMessageTemplate());
        assertNotNull(getViolation("requestID", violations));
        assertEquals("{jakarta.validation.constraints.Size.message}", getViolation("requestID", violations).getMessageTemplate());
    }

    @Test
    void testValidateGivenFieldLengthMoreThanMaxThenInvalid() {
        // given
        LogRequest request = new LogRequest("more than 20 characters", "more than 20 characters", Severity.DEBUG, System.currentTimeMillis(), longMessage(), "more than 20 characters", "more than 20 characters");

        // when
        Set<ConstraintViolation<LogRequest>> violations = validator.validate(request);

        // then
        assertNotNull(getViolation("applicationID", violations));
        assertEquals("{jakarta.validation.constraints.Size.message}", getViolation("applicationID", violations).getMessageTemplate());
        assertNotNull(getViolation("traceID", violations));
        assertEquals("{jakarta.validation.constraints.Size.message}", getViolation("traceID", violations).getMessageTemplate());
        assertNotNull(getViolation("message", violations));
        assertEquals("{jakarta.validation.constraints.Size.message}", getViolation("message", violations).getMessageTemplate());
        assertNotNull(getViolation("componentName", violations));
        assertEquals("{jakarta.validation.constraints.Size.message}", getViolation("componentName", violations).getMessageTemplate());
        assertNotNull(getViolation("requestID", violations));
        assertEquals("{jakarta.validation.constraints.Size.message}", getViolation("requestID", violations).getMessageTemplate());
    }

    private String longMessage() {
        return new Random().ints('a', 'z' + 1)
                .limit(201)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private ConstraintViolation<LogRequest> getViolation(String property, Set<ConstraintViolation<LogRequest>> violations) {
        return violations.stream().filter(item -> item.getPropertyPath().toString().equals(property)).findFirst().orElse(null);
    }
}
