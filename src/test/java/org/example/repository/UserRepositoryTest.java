package org.example.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface UserRepositoryTest {

    @Test
    void findByIdPositive();

    @Test
    void findByIdNegative();

    @Test
    void create();

    @Test
    void update();

    @Test
    void delete();
}
