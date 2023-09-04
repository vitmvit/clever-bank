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
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;


public class UserServiceTestImpl implements UserServiceTest {

    private final UserService userService = Mockito.mock(UserServiceImpl.class);

    @Test
    public void findByIdPositive() {
        UserResponseDto target = getUserResponseDto();
        when(userService.findById(1L)).thenReturn(target);
        UserResponseDto result = userService.findById(target.getId());
        assertEquals(target.getId(), result.getId());
        assertEquals(target.getName(), result.getName());
    }

    @Test
    public void findByIdNegative() {
        when(userService.findById(anyLong())).thenThrow(new ConnectionException("Connection is lost"));
        var exception = Assertions.assertThrows(Exception.class, () -> {
            userService.findById(Long.MAX_VALUE);
        });
        Assertions.assertTrue(exception.getMessage().startsWith("Connection is lost"));
    }

    @Test
    public void createPositive() {
        UserResponseDto target = getUserResponseDto();
        UserCreateDto userCreateDto = getUserCreateDto();
        when(userService.create(userCreateDto)).thenReturn(target);
        UserResponseDto actualResponse = userService.create(userCreateDto);
        Assertions.assertNotNull(actualResponse.getId());
        Assertions.assertEquals(userCreateDto.name(), actualResponse.getName());
    }

    @Test
    public void createNegative() {
        UserCreateDto target = new UserCreateDto(null);
        doThrow(RequestException.class).when(userService).create(target);
        var exception = Assertions.assertThrows(Exception.class,
                () -> userService.create(target));
        Assertions.assertEquals(exception.getClass(), RequestException.class);
    }

    @Test
    public void updatePositive() {
        UserCreateDto accountCreateDto = getUserCreateDto();
        UserResponseDto saved = getUserResponseDto();
        when(userService.create(accountCreateDto)).thenReturn(saved);
        UserUpdateDto user = getUserUpdateDto();
        UserResponseDto updated = new UserResponseDto();
        updated.setId(saved.getId());
        updated.setName("user_update_name");
        when(userService.update(user)).thenReturn(updated);
        Assertions.assertNotEquals(saved.getName(), updated.getName());
    }

    @Test
    public void updateNegative() {
        UserUpdateDto user = new UserUpdateDto();
        user.setId(null);
        user.setName(null);
        when(userService.update(user)).thenThrow(new RequestException("String is empty"));
        RequestException exception = Assertions.assertThrows(RequestException.class, () -> {
            userService.update(user);
        });
        Assertions.assertTrue(exception.getMessage().startsWith("String is empty"));
    }

    @Test
    public void delete() {
        UserCreateDto userCreateDto = getUserCreateDto();
        UserResponseDto saved = getUserResponseDto();
        doReturn(saved).when(userService).create(userCreateDto);
        doNothing().when(userService).delete(saved.getId());
        doThrow(new ConnectionException("Connection is lost")).when(userService).findById(saved.getId());

        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            userService.findById(saved.getId());
        });
        Assertions.assertTrue(exception.getMessage().startsWith("Connection is lost"));
    }

    private UserResponseDto getUserResponseDto() {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(1L);
        userResponseDto.setName("user_1");
        return userResponseDto;
    }

    private UserCreateDto getUserCreateDto() {
        return new UserCreateDto("user_1");
    }

    private UserUpdateDto getUserUpdateDto() {
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setId(1L);
        userUpdateDto.setName("user_1");
        return userUpdateDto;
    }
}