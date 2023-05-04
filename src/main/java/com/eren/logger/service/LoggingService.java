package com.eren.logger.service;

import com.eren.logger.model.LogRequest;

public interface LoggingService {

    void push(LogRequest request) throws LogDestinationException;

}
