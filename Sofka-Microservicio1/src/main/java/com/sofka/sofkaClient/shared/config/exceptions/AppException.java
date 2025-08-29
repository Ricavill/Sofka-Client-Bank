package com.sofka.sofkaClient.shared.config.exceptions;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String code;
    private final Object args;

    public AppException(String message, HttpStatus httpStatus, String code, Object... args) {
        super(message);
        this.httpStatus = httpStatus;
        this.code = code;
        this.args = args;
    }

    public AppException(String message, Throwable cause, HttpStatus httpStatus, String code, Object... args) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.code = code;
        this.args = args;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }
}
