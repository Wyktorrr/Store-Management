package com.store.api.management.user.service;

import com.store.api.management.user.model.UserDTO;
import com.store.api.management.user.model.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> findAll();
    Optional<UserDTO> findById(Long id);
    UserDTO save(User user);
    UserDTO updateUser(UserDTO userDTO);
    void deleteById(Long id);
}
