package com.example.sofkatransaction.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum AccountType {
    SAVINGS(0, "Ahorro"), CHECKING(1, "Corriente");

    private final int code;
    private final String description;

    private AccountType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonCreator
    public static AccountType fromCode(int code) {
        return Arrays.stream(values())
                .filter(e -> e.code == code)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid code: " + code));
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }


    }
