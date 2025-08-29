package com.sofka.sofkaClient.shared.config.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class EntityNotFoundException extends AppException {
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public EntityNotFoundException(String message) {
        super(message, STATUS, null);
    }


    public EntityNotFoundException(String code, Object... args) {
        super("Entity Not Found", STATUS, code, args);
    }

    public EntityNotFoundException(Class<?> entityClass, Map<String, Object> fieldValues) {
        super(notFoundMessage(entityClass, fieldValues), STATUS,
                "NotFoundException", entityClass.getName(), fieldValues);
    }

    private static String notFoundMessage(Class<?> entityClass, Map<String, Object> fieldValues) {
        StringBuilder sb = new StringBuilder();
        sb.append("Not found entity ")
                .append(entityClass.getSimpleName())
                .append(" with ");

        String fields = fieldValues.entrySet().stream()
                .map(e -> "field '" + e.getKey() + "' of value '" + e.getValue() + "'")
                .reduce((a, b) -> a + ", " + b)
                .orElse("no fields");

        sb.append(fields);
        return sb.toString();
    }

}
