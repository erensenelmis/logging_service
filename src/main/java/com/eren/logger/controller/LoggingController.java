package com.eren.logger.controller;

import com.eren.logger.model.LogRequest;
import com.eren.logger.model.LogResponse;
import com.eren.logger.service.LogDestinationException;
import com.eren.logger.service.LoggingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoggingController {

    private final Logger logger = LoggerFactory.getLogger(LoggingController.class);

    @Autowired
    private LoggingService service;

    @GetMapping("/status")
    String status() {
        return "OK";
    }

    @PostMapping("/log")
    ResponseEntity<LogResponse> log(@Valid @RequestBody LogRequest request) {
        logger.info("request = {}", request);
        try {
            service.push(request);
            return response("Success", "The log message stored successfully", HttpStatus.CREATED);
        } catch (LogDestinationException e) {
            logger.error("Error in destination", e);
            return response("DestinationFailed", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Unknown exception", e);
            return response("UnknownError", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<LogResponse> response(String code, String message, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new LogResponse(code, message));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
