package org.example.repository.impl;

import org.example.exeption.ConnectionException;
import org.example.model.entity.User;
import org.example.repository.UserRepository;
import org.example.repository.UserRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.example.model.constant.Constants.CONNECTION_EXCEPTION_MESSAGE;

public class UserRepositoryTestImpl implements UserRepositoryTest {

    private final UserRepository userRepository = new UserRepositoryImpl();

    @Test
    public void findByIdPositiveTest() {
        User user = userRepository.create(getUser());
        Assertions.assertNotNull(userRepository.findById(user.getId()));

        userRepository.delete(user.getId());
    }

    @Test
    public void findByIdNegativeTest() {
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            userRepository.findById(Long.MAX_VALUE);
        });
        Assertions.assertTrue(exception.getMessage().startsWith(CONNECTION_EXCEPTION_MESSAGE));
    }

    @Test
    public void createTest() {
        User userCreate = getUser();
        User saved = userRepository.create(getUser());
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(userCreate.getName(), saved.getName());

        userRepository.delete(saved.getId());
    }

    @Test
    public void updateTest() {
        User saved = userRepository.create(getUser());
        User user = new User();
        user.setId(saved.getId());
        user.setName("User_1");
        User updated = userRepository.update(user);
        Assertions.assertNotEquals(saved.getName(), updated.getName());

        userRepository.delete(saved.getId());
    }

    @Test
    public void deleteTest() {
        User saved = userRepository.create(getUser());
        userRepository.delete(saved.getId());
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            userRepository.findById(saved.getId());
        });
        Assertions.assertTrue(exception.getMessage().startsWith(CONNECTION_EXCEPTION_MESSAGE));

        userRepository.delete(saved.getId());
    }

    private User getUser() {
        User user = new User();
        user.setName("user_1");
        return user;
    }
}
