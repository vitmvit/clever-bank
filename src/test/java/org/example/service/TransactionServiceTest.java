package org.example.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface TransactionServiceTest {
    // TODO: упал
    @Test
    void findByIdPositive();

    @Test
    void findByIdNegative();

    // TODO: упал
    @Test
    void getAllTransactionsByUserIdPositive();

    @Test
    void getAllTransactionsByUserIdNegative();

    // TODO: упал
    @Test
    void getAllTransactionsByDatePositive();

    // TODO: упал
    @Test
    void getAllTransactionsByDateNegative();

    // TODO: упал
    @Test
    void createPositive();

    @Test
    void createNegative();

    // TODO: упал
    @Test
    void updatePositive();

    @Test
    void updateNegative();

    // TODO: упал
    @Test
    void delete();
}
