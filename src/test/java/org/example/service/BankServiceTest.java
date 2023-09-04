package org.example.service;

import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface BankServiceTest {

    void findByIdPositiveTest();

    void findByIdNegativeTest();

    void createPositiveTest();

    void createNegativeTest();

    void updatePositiveTest();

    void updateNegativeTest();

    void deleteTest();
}
