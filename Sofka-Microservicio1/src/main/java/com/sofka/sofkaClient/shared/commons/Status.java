package com.sofka.sofkaClient.shared.commons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Status {
    DISABLED(0), ACTIVE(1);

    private final int code;

    Status(int code) {
        this.code = code;
    }

    @JsonCreator
    public static Status forCode(int code) {
        return Arrays.stream(values())
                .filter(e -> e.getCode() == code)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid Status code: " + code));
    }

    @JsonValue
    public int getCode() {
        return code;
    }
}
