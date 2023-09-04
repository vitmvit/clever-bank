package org.example.model.constant;

import lombok.Getter;

public enum TransactionType {

    A("A"), // add money
    R("R"), // remove money
    T("T"); // transfer money

    @Getter
    private String name;

    TransactionType(String name) {
        this.name = name;
    }
}
