package org.example.repository;

import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface BankRepositoryTest {

    void findByIdPositiveTest();

    void findByIdNegativeTest();

    void createTest();

    void updateTest();

    void deleteTest();
}
