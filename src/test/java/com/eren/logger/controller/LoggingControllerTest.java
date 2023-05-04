package com.eren.logger.controller;

import com.eren.logger.model.LogRequest;
import com.eren.logger.model.Severity;
import com.eren.logger.service.LogDestinationException;
import com.eren.logger.service.LoggingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class LoggingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoggingService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testLogGivenAllRequiredSetThenSuccess() throws Exception {
        // given
        LogRequest request = new LogRequest("application-01", "trace-01", Severity.DEBUG, System.currentTimeMillis(), "Sample log 001");

        // when
        ResultActions response = mockMvc.perform(post("/log")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is("Success")))
                .andExpect(jsonPath("$.message", is("The log message stored successfully")));
    }

    @Test
    void testLogGivenDestinationFailedThenError() throws Exception {
        // given
        doThrow(new LogDestinationException(new Exception("An error happened"))).when(service).push(any());
        LogRequest request = new LogRequest("application-01", "trace-01", Severity.DEBUG, System.currentTimeMillis(), "Sample log 001");

        // when
        ResultActions response = mockMvc.perform(post("/log")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        response.andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code", is("DestinationFailed")))
                .andExpect(jsonPath("$.message", is("java.lang.Exception: An error happened")));
    }

    @Test
    void testLogGivenUnknownFailureThenError() throws Exception {
        // given
        doThrow(new RuntimeException(new Exception("An error happened"))).when(service).push(any());
        LogRequest request = new LogRequest("application-01", "trace-01", Severity.DEBUG, System.currentTimeMillis(), "Sample log 001");

        // when
        ResultActions response = mockMvc.perform(post("/log")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        response.andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code", is("UnknownError")))
                .andExpect(jsonPath("$.message", is("java.lang.Exception: An error happened")));
    }

    @Test
    void testLogGivenAllNullThenError() throws Exception {
        // given
        LogRequest request = new LogRequest();

        // when
        ResultActions response = mockMvc.perform(post("/log")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.applicationID", is("must not be null")))
                .andExpect(jsonPath("$.traceID", is("must not be null")))
                .andExpect(jsonPath("$.severity", is("must not be null")))
                .andExpect(jsonPath("$.timestamp", is("must not be null")))
                .andExpect(jsonPath("$.message", is("must not be null")))
        ;
    }
}
