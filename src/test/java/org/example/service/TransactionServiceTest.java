package org.example.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface TransactionServiceTest {
    @Test
    void findByIdPositive();

    @Test
    void findByIdNegative();

    @Test
    void getAllTransactionsByUserIdPositive();

    @Test
    void getAllTransactionsByUserIdNegative();

    @Test
    void getAllTransactionsByDatePositive();

    @Test
    void getAllTransactionsByDateNegative();

    @Test
    void createPositive();

    @Test
    void createNegative();

    @Test
    void updatePositive();

    @Test
    void updateNegative();

    @Test
    void delete();
}
