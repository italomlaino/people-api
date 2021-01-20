package com.italomlaino.peopleapi.domain.exception;

import org.springframework.http.HttpStatus;

public class PersonNotFoundException extends DomainException {
    public PersonNotFoundException() {
        super("resource not found", HttpStatus.NOT_FOUND);
    }
}
