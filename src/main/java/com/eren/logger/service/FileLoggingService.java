package com.eren.logger.service;

import com.eren.logger.model.LogRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.InvalidPathException;

@Service
@ConditionalOnProperty(prefix = "logger", name = "implementation", havingValue = "file")
public class FileLoggingService implements LoggingService {

    private static final String FILE_NAME = "log-storage.txt";

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void push(LogRequest request) throws LogDestinationException {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            printWriter.println(objectMapper.writeValueAsString(request));
        } catch (InvalidPathException | IOException e) {
            throw new LogDestinationException(e);
        }
    }
}
