package com.store.api.management.user.service.impl;

import com.store.api.management.exception.ConflictException;
import com.store.api.management.user.model.UserDTO;
import com.store.api.management.user.model.domain.User;
import com.store.api.management.user.model.mapper.UserMapper;
import com.store.api.management.user.repository.UserRepository;
import com.store.api.management.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return userMapper.userListToUserDTOList(users);
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id).map(userMapper::userToUserDTO);
    }

    @Override
    public UserDTO save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException("User with email " + user.getEmail() + " already exists.");
        }

        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        return userMapper.userToUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userDTO.getId()));
        User existingUser;

        existingUser = userMapper.userDTOToUser(userDTO);
        existingUser.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        return userMapper.userToUserDTO(userRepository.save(existingUser));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}