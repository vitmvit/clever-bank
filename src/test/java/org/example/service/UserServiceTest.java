package org.example.service;

import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface UserServiceTest {

    void findByIdPositive();

    void findByIdNegative();

    void createPositive();

    void createNegative();

    void updatePositive();

    void updateNegative();

    void delete();
}
