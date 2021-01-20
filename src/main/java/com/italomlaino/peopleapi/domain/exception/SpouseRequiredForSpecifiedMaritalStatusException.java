package com.italomlaino.peopleapi.domain.exception;

import org.springframework.http.HttpStatus;

public class SpouseRequiredForSpecifiedMaritalStatusException extends DomainException {
    public SpouseRequiredForSpecifiedMaritalStatusException() {
        super("spouse fields required for the specified marital status", HttpStatus.BAD_REQUEST);
    }
}
