package com.store.api.management.user.service.impl;

import com.store.api.management.user.model.domain.User;
import com.store.api.management.user.model.UserDTO;
import com.store.api.management.user.model.mapper.UserMapper;
import com.store.api.management.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper; // Mocked UserMapper

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
    }

    @Test
    public void testSave() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.userToUserDTO(user)).thenReturn(userDTO);

        UserDTO savedUserDTO = userService.save(user);
        assertNotNull(savedUserDTO);
        assertEquals(user.getUsername(), savedUserDTO.getUsername());
        verify(userRepository).save(any(User.class));
        verify(userMapper).userToUserDTO(user);
    }

    @Test
    public void testFindByIdSuccess() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.userToUserDTO(user)).thenReturn(userDTO);

        Optional<UserDTO> foundUserDTO = userService.findById(user.getId());
        assertTrue(foundUserDTO.isPresent());
        assertEquals(user.getUsername(), foundUserDTO.get().getUsername());
        verify(userRepository).findById(user.getId());
        verify(userMapper).userToUserDTO(user);
    }

    @Test
    public void testDeleteById() {
        userService.deleteById(user.getId());
        verify(userRepository).deleteById(user.getId());
    }
}