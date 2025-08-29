package com.sofka.sofkaClient.shared.config.exceptions;

import org.springframework.http.HttpStatus;

public class FunctionalException extends AppException {
    private static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public FunctionalException(String message, Throwable cause) {
        super(message, cause, STATUS, null);
    }
}
