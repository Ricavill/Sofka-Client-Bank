package com.sofka.sofkaClient.shared.config.exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ValidationException extends AppException {
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
    protected List<Object> errors;


    public ValidationException(String code, Object args) {
        super("Validation Exception", STATUS, code, args);
    }

    public ValidationException(HttpStatus httpStatus, String code, Object args) {
        super("Validation Exception", httpStatus, code, args);
    }

    public ValidationException(Throwable cause, String code, Object args) {
        super("Validation Exception", cause, STATUS, code, args);
    }

    public ValidationException(String message) {
        super(message, STATUS, null);
    }

}
