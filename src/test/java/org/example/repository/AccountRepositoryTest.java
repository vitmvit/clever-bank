package org.example.repository;

import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface AccountRepositoryTest {

    void findByIdPositiveTest();

    void findByIdNegativeTest();

    void createTest();

    void updateTest();

    void deleteTest();

    void moneyTransferPositiveTest();

    void moneyTransferNegativeTest();
}