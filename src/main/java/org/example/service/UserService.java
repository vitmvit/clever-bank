package org.example.service;

import org.example.model.dto.request.UserCreateDto;
import org.example.model.dto.response.UserResponseDto;
import org.example.model.dto.response.UserUpdateDto;

public interface UserService {

    UserResponseDto findById(Long id);

    UserResponseDto create(UserCreateDto userCreateDto);

    UserResponseDto update(UserUpdateDto userUpdateDto);

    void delete(Long id);
}
