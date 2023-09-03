package org.example.service.impl;

import org.example.exeption.ConnectionException;
import org.example.exeption.RequestException;
import org.example.model.dto.request.UserCreateDto;
import org.example.model.dto.response.UserResponseDto;
import org.example.model.dto.response.UserUpdateDto;
import org.example.service.UserService;
import org.example.service.UserServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class UserServiceTestImpl implements UserServiceTest {

    private final UserService userService = new UserServiceImpl();

    @Test
    public void findByIdPositive() {
        UserResponseDto userResponseDto = userService.create(getUser());
        Assertions.assertNotNull(userService.findById(userResponseDto.getId()));
    }

    @Test
    public void findByIdNegative() {
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            userService.findById(Long.MAX_VALUE);
        });
        Assertions.assertTrue(exception.getMessage().startsWith("Connection is lost"));
    }

    @Test
    public void createPositive() {
        UserCreateDto userCreateDto = getUser();
        UserResponseDto userResponseDto = userService.create(userCreateDto);
        Assertions.assertNotNull(userResponseDto.getId());
        Assertions.assertEquals(userCreateDto.name(), userResponseDto.getName());
    }

    @Test
    public void createNegative() {
        UserCreateDto userCreateDto = new UserCreateDto(null);
        var exception = Assertions.assertThrows(Exception.class,
                () -> userService.create(userCreateDto));
        Assertions.assertEquals(exception.getClass(), RequestException.class);
    }

    @Test
    public void updatePositive() {
        UserCreateDto accountCreateDto = getUser();

        UserResponseDto saved = userService.create(accountCreateDto);

        UserUpdateDto user = new UserUpdateDto();
        user.setId(saved.getId());
        user.setName("Name");

        UserResponseDto updated = userService.update(user);
        Assertions.assertNotEquals(saved.getName(), updated.getName());
    }

    @Test
    public void updateNegative() {
        UserUpdateDto user = new UserUpdateDto();
        user.setId(null);
        user.setName(null);

        RequestException exception = Assertions.assertThrows(RequestException.class, () -> {
            userService.update(user);
        });
        Assertions.assertTrue(exception.getMessage().startsWith("String is empty"));
    }

    @Test
    public void delete() {
        UserResponseDto saved = userService.create(getUser());
        userService.delete(saved.getId());
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            userService.findById(saved.getId());
        });
        Assertions.assertTrue(exception.getMessage().startsWith("Connection is lost"));
    }

    private UserCreateDto getUser() {
        return new UserCreateDto("user_1");
    }
}