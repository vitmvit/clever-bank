package org.example.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface TransactionRepositoryTest {

    @Test
    void findByIdPositive();

    @Test
    void findByIdNegative();

    // TODO: упал
    @Test
    void getAllTransactionsByUserIdPositive();

    @Test
    void getAllTransactionsByUserIdNegative();

    @Test
    void getAllTransactionsByDatePositive();

    @Test
    void getAllTransactionsByDateNegative();

    @Test
    void create();

    // TODO: упал
    @Test
    void update();

    @Test
    void delete();
}
