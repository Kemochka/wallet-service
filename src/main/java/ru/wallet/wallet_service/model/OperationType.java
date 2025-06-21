package ru.wallet.wallet_service.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum OperationType {
    DEPOSIT, WITHDRAW;

    @JsonCreator
    public static OperationType from(String value) {
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid operation type: " + value));
    }
}
