package org.example.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface BankServiceTest {

    @Test
    void findByIdPositive();

    @Test
    void findByIdNegative();

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
