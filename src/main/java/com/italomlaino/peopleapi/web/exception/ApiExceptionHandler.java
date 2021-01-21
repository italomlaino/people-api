package com.italomlaino.peopleapi.web.exception;

import com.italomlaino.peopleapi.domain.exception.DomainException;
import com.italomlaino.peopleapi.domain.view.ApiException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = getBindingErrors(ex.getBindingResult());

        var apiException = new ApiException(
                HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(),
                errors
        );
        return handleExceptionInternal(
                ex, apiException, headers, apiException.getStatus(), request);
    }


    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Object> handleDomainException(DomainException ex) {
        var apiError = new ApiException(
                ex.getResponseStatus(),
                "Error",
                ex.getLocalizedMessage()
        );
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex) {
        var apiError = new ApiException(
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), ex.getLocalizedMessage());
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var apiError = new ApiException(
                HttpStatus.BAD_REQUEST, "Validation error", ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        for (var error : ex.getConstraintViolations()) {
            errors.add(error.getPropertyPath() + ": " + error.getMessage());
        }

        var apiError = new ApiException(
                HttpStatus.BAD_REQUEST,
                "Validation error",
                errors
        );
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<Object> handleTransactionSystemException(TransactionSystemException ex) {
        if (ex.getRootCause() instanceof ConstraintViolationException) {
            var constraintViolationException = (ConstraintViolationException) ex.getRootCause();
            return handleConstraintViolationException(constraintViolationException);
        }

        var apiError = new ApiException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Transaction error",
                ex.getLocalizedMessage()
        );
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        var apiError = new ApiException(
                HttpStatus.BAD_REQUEST, "Validation error", ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    private List<String> getBindingErrors(BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();
        for (var error : bindingResult.getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (var error : bindingResult.getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        return errors;
    }
}
