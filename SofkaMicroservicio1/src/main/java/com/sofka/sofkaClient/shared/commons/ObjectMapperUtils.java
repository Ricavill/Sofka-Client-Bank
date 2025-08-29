package com.sofka.sofkaClient.shared.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofka.sofkaClient.shared.config.exceptions.FunctionalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class ObjectMapperUtils {

    private static ObjectMapper mapper;

    @Autowired
    public ObjectMapperUtils(ObjectMapper mapper) {
        setMapper(mapper);
    }

    private static void setMapper(ObjectMapper objectMapper) {
        mapper = objectMapper;
    }


    public static <T> T toObject(InputStream content, Class<T> valueType) {
        try {
            return mapper.readValue(content, valueType);
        } catch (IOException e) {
            String message = "Error to convert into Java Object";
            throw new FunctionalException(message, e);
        }
    }

    public static <T> String toString(T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            String message = "Error to convert object into String";
            throw new FunctionalException(message, e);
        }
    }
}
