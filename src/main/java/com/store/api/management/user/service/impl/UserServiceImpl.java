package com.store.api.management.user.service.impl;

import com.store.api.management.user.model.UserDTO;
import com.store.api.management.user.model.domain.User;
import com.store.api.management.user.model.mapper.UserMapper;
import com.store.api.management.user.repository.UserRepository;
import com.store.api.management.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public UserDTO save(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        return userMapper.userToUserDTO(userRepository.save(user));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}