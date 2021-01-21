package com.italomlaino.peopleapi.domain.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ApiException {

    @JsonIgnore
    private final HttpStatus status;
    private final String message;
    private final List<String> errors;

    public ApiException(HttpStatus status, String message, String error) {
        this(status, message, List.of(error));
    }

    public ApiException(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}
