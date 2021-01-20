package com.italomlaino.peopleapi.domain.exception;

import org.springframework.http.HttpStatus;

public class DomainException extends RuntimeException {

    private final HttpStatus responseStatus;

    public DomainException(String message, HttpStatus responseStatus) {
        super(message);

        this.responseStatus = responseStatus;
    }

    public HttpStatus getResponseStatus() {
        return responseStatus;
    }
}
