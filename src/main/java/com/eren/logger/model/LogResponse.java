package com.eren.logger.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LogResponse {

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    public LogResponse() {
    }

    public LogResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
