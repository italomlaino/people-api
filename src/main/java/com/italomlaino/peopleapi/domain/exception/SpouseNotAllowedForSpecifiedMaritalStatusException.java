package com.italomlaino.peopleapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class SpouseNotAllowedForSpecifiedMaritalStatusException extends DomainException {
    public SpouseNotAllowedForSpecifiedMaritalStatusException() {
        super("spouse fields not allowed for the specified marital status", HttpStatus.BAD_REQUEST);
    }
}
