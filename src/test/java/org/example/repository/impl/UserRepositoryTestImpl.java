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
    public void findByIdPositive() {
        User user = userRepository.create(getUser());
        Assertions.assertNotNull(userRepository.findById(user.getId()));
    }

    @Test
    public void findByIdNegative() {
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            userRepository.findById(Long.MAX_VALUE);
        });
        Assertions.assertTrue(exception.getMessage().startsWith(CONNECTION_EXCEPTION_MESSAGE));
    }

    @Test
    public void create() {
        User userCreate = getUser();
        User saved = userRepository.create(getUser());
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(userCreate.getName(), saved.getName());
    }

    @Test
    public void update() {
        User saved = userRepository.create(getUser());
        User user = new User();
        user.setId(saved.getId());
        user.setName("User_1");
        User updated = userRepository.update(user);
        Assertions.assertNotEquals(saved.getName(), updated.getName());
    }

    @Test
    public void delete() {
        User saved = userRepository.create(getUser());
        userRepository.delete(saved.getId());
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            userRepository.findById(saved.getId());
        });
        Assertions.assertTrue(exception.getMessage().startsWith(CONNECTION_EXCEPTION_MESSAGE));
    }

    private User getUser() {
        User user = new User();
        user.setName("user_1");
        return user;
    }
}
