package org.example.converter;

import org.example.model.dto.request.UserCreateDto;
import org.example.model.dto.response.UserResponseDto;
import org.example.model.dto.response.UserUpdateDto;
import org.example.model.entity.User;

public class UserConverter {

    public User convert(UserCreateDto source) {
        if (source == null) {
            return null;
        }
        User target = new User();
        target.setName(source.name());
        return target;
    }

    public User convert(UserUpdateDto source) {
        if (source == null) {
            return null;
        }
        User target = new User();
        target.setId(source.getId());
        target.setName(source.getName());
        return target;
    }

    public UserResponseDto convert(User source) {
        if (source == null) {
            return null;
        }
        UserResponseDto target = new UserResponseDto();
        target.setId(source.getId());
        target.setName(source.getName());
        return target;
    }
}
