package com.example.sofkatransaction.transaction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum TransactionType {
    WITHDRAW(1), DEPOSIT(2);

    private final int code;

    TransactionType(int code) {
        this.code = code;
    }

    @JsonCreator
    public static TransactionType forCode(int code) {
        return Arrays.stream(values())
                .filter(e -> e.getCode() == code)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid TransactionType code: " + code));
    }

    @JsonValue
    public int getCode() {
        return code;
    }
}
