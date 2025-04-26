package com.store.api.management.user.service;

import com.store.api.management.user.model.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> findAll();
    Optional<UserDTO> findById(Long id);
    UserDTO save(UserDTO userDTO);
    void deleteById(Long id);
}
