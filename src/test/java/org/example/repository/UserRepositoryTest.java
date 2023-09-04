package org.example.repository;

import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface UserRepositoryTest {

    void findByIdPositiveTest();

    void findByIdNegativeTest();

    void createTest();

    void updateTest();

    void deleteTest();
}
