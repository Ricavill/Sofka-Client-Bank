package com.sofka.sofkaClient.shared.config.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<Map<String, Object>> handleAppException(AppException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", e.getMessage());
        body.put("status", e.getHttpStatus().toString());
        body.put("code", e.getCode());
        body.put("error", e.getHttpStatus().getReasonPhrase());
        return new ResponseEntity<>(body, e.getHttpStatus());
    }

}
