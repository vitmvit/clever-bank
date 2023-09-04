package org.example.repository;

import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface TransactionRepositoryTest {

    void findByIdPositiveTest();

    void findByIdNegativeTest();

    void getAllTransactionsByUserIdPositiveTest();

    void getAllTransactionsByUserIdNegativeTest();

    void getAllTransactionsByDatePositiveTest();

    void getAllTransactionsByDateNegativeTest();

    void createTest();

    void updateTest();

    void deleteTest();
}
