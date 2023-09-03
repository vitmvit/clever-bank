package org.example.service.impl;

import org.example.converter.UserConverter;
import org.example.exeption.RequestException;
import org.example.model.dto.request.UserCreateDto;
import org.example.model.dto.response.UserResponseDto;
import org.example.model.dto.response.UserUpdateDto;
import org.example.model.entity.User;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.UserService;
import org.example.util.StringUtils;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository = new UserRepositoryImpl();
    private final UserConverter userConverter = new UserConverter();

    @Override
    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id);
        return userConverter.convert(user);
    }

    @Override
    public UserResponseDto create(UserCreateDto userCreateDto) {
        if (StringUtils.isEmpty(userCreateDto.name())) {
            throw new RequestException("Name is empty");
        }
        User user = userConverter.convert(userCreateDto);
        User saved = userRepository.create(user);
        return userConverter.convert(saved);
    }

    @Override
    public UserResponseDto update(UserUpdateDto userUpdateDto) {
        if (StringUtils.isEmpty(userUpdateDto.getName())) {
            throw new RequestException("String is empty");
        }
        User user = userConverter.convert(userUpdateDto);
        User saved = userRepository.update(user);
        return userConverter.convert(saved);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }
}
