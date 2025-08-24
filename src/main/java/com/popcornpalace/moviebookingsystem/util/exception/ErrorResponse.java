package com.popcornpalace.moviebookingsystem.util.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    public OffsetDateTime timestamp = OffsetDateTime.now();
    public int status;
    public String error;
    public String message;
    public String path;
    public List<FieldError> details;

    public ErrorResponse(int status, String error, String message, String path, List<FieldError> details) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.details = details;
    }

    public static class FieldError {
        public String field;
        public String issue;

        public FieldError(String field, String issue) {
            this.field = field; this.issue = issue;
        }
    }
}