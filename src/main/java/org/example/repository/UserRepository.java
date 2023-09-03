package org.example.repository;

import org.example.model.entity.User;

public interface UserRepository {

    User findById(Long id);

    User create(User user);

    User update(User user);

    void delete(Long id);
}
