package com.eren.logger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LogRequest {

    @JsonProperty("applicationID")
    @NotNull
    @Size(min=3, max=20)
    private String applicationID;

    @JsonProperty("traceID")
    @NotNull
    @Size(min=3, max=20)
    private String traceID;

    @JsonProperty("severity")
    @NotNull
    private Severity severity;

    @JsonProperty("timestamp")
    @NotNull
    private Long timestamp;

    @JsonProperty("message")
    @NotNull
    @Size(min=3, max=200)
    private String message;

    @JsonProperty("componentName")
    @Size(min=3, max=20)
    private String componentName;

    @JsonProperty("requestID")
    @Size(min=3, max=20)
    private String requestID;

    public LogRequest() {
    }

    public LogRequest(String applicationID, String traceID, Severity severity, Long timestamp, String message) {
        this.applicationID = applicationID;
        this.traceID = traceID;
        this.message = message;
        this.severity = severity;
        this.timestamp = timestamp;
    }

    public LogRequest(String applicationID, String traceID, Severity severity, Long timestamp, String message, String componentName, String requestID) {
        this.applicationID = applicationID;
        this.traceID = traceID;
        this.severity = severity;
        this.timestamp = timestamp;
        this.message = message;
        this.componentName = componentName;
        this.requestID = requestID;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    public String getTraceID() {
        return traceID;
    }

    public void setTraceID(String traceID) {
        this.traceID = traceID;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    @Override
    public String toString() {
        return "LogRequest{" +
                "applicationID='" + applicationID + '\'' +
                ", traceID='" + traceID + '\'' +
                ", severity=" + severity +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", componentName='" + componentName + '\'' +
                ", requestID='" + requestID + '\'' +
                '}';
    }
}
