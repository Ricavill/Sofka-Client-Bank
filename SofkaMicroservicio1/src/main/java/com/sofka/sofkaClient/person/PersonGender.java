package com.sofka.sofkaClient.person;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum PersonGender {
    MALE(1), FEMALE(2), OTHER(3);

    private final int code;

    PersonGender(int code) {
        this.code = code;
    }

    @JsonCreator
    public static PersonGender forCode(int code) {
        return Arrays.stream(values())
                .filter(e -> e.getCode() == code)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid Status code: " + code));
    }

    @JsonValue
    public int getCode() {
        return code;
    }
}
