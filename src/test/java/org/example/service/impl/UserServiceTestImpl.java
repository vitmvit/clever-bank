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

import static org.example.model.constant.Constants.CONNECTION_EXCEPTION_MESSAGE;
import static org.example.model.constant.Constants.REQUEST_EXCEPTION_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;


public class UserServiceTestImpl implements UserServiceTest {

    private final UserService userService = Mockito.mock(UserServiceImpl.class);

    @Test
    public void findByIdPositiveTest() {
        UserResponseDto target = getUserResponseDto();
        when(userService.findById(1L)).thenReturn(target);
        UserResponseDto result = userService.findById(target.getId());
        assertEquals(target.getId(), result.getId());
        assertEquals(target.getName(), result.getName());
    }

    @Test
    public void findByIdNegativeTest() {
        when(userService.findById(anyLong())).thenThrow(new ConnectionException(CONNECTION_EXCEPTION_MESSAGE));
        var exception = Assertions.assertThrows(Exception.class, () -> {
            userService.findById(Long.MAX_VALUE);
        });
        Assertions.assertTrue(exception.getMessage().startsWith(CONNECTION_EXCEPTION_MESSAGE));
    }

    @Test
    public void createPositiveTest() {
        UserResponseDto target = getUserResponseDto();
        UserCreateDto userCreateDto = getUserCreateDto();
        when(userService.create(userCreateDto)).thenReturn(target);
        UserResponseDto actualResponse = userService.create(userCreateDto);
        Assertions.assertNotNull(actualResponse.getId());
        Assertions.assertEquals(userCreateDto.name(), actualResponse.getName());
    }

    @Test
    public void createNegativeTest() {
        UserCreateDto target = new UserCreateDto(null);
        doThrow(RequestException.class).when(userService).create(target);
        var exception = Assertions.assertThrows(Exception.class,
                () -> userService.create(target));
        Assertions.assertEquals(exception.getClass(), RequestException.class);
    }

    @Test
    public void updatePositiveTest() {
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
    public void updateNegativeTest() {
        UserUpdateDto user = new UserUpdateDto();
        user.setId(null);
        user.setName(null);
        when(userService.update(user)).thenThrow(new RequestException(REQUEST_EXCEPTION_MESSAGE));
        RequestException exception = Assertions.assertThrows(RequestException.class, () -> {
            userService.update(user);
        });
        Assertions.assertTrue(exception.getMessage().startsWith(REQUEST_EXCEPTION_MESSAGE));
    }

    @Test
    public void deleteTest() {
        UserCreateDto userCreateDto = getUserCreateDto();
        UserResponseDto saved = getUserResponseDto();
        doReturn(saved).when(userService).create(userCreateDto);
        doNothing().when(userService).delete(saved.getId());
        doThrow(new ConnectionException(CONNECTION_EXCEPTION_MESSAGE)).when(userService).findById(saved.getId());
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            userService.findById(saved.getId());
        });
        Assertions.assertTrue(exception.getMessage().startsWith(CONNECTION_EXCEPTION_MESSAGE));
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