package org.example.service;

import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface TransactionServiceTest {

    void findByIdPositiveTest();

    void findByIdNegativeTest();

    void getAllTransactionsByUserIdPositiveTest();

    void getAllTransactionsByUserIdNegativeTest();

    void getAllTransactionsByDatePositiveTest();

    void getAllTransactionsByDateNegativeTest();

    void createPositiveTest();

    void createNegativeTest();

    void updatePositiveTest();

    void updateNegativeTest();

    void deleteTest();
}
