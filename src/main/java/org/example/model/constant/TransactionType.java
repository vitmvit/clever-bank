package org.example.model.constant;

import lombok.Getter;

public enum TransactionType {

    ADD("A"),
    REMOVE("R"),
    TRANSFER("T");

    @Getter
    private String name;

    TransactionType(String name) {
        this.name = name;
    }
}
