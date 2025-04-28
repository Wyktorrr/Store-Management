package com.store.api.management.auth;

import com.store.api.management.exception.InvalidCredentialsException;
import com.store.api.management.user.model.UserDTO;
import com.store.api.management.user.model.domain.User;
import com.store.api.management.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private AuthDTO validAuthDTO;
    private AuthDTO invalidAuthDTO;

    @BeforeEach
    public void setUp() {
        validAuthDTO = new AuthDTO();
        validAuthDTO.setUsername("validUser");
        validAuthDTO.setPassword("validPassword");

        invalidAuthDTO = new AuthDTO();
        invalidAuthDTO.setUsername("invalidUser");
        invalidAuthDTO.setPassword("invalidPassword");
    }

    @Test
    public void testLoginSuccess() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(validAuthDTO.getUsername())).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(validAuthDTO.getUsername());

        String token = authService.login(validAuthDTO);
        assertNotNull(token);
        verify(authenticationManager).authenticate(any());
    }

    @Test
    public void testLoginInvalidCredentials() {
        when(userDetailsService.loadUserByUsername(invalidAuthDTO.getUsername())).thenReturn(null);

        Exception exception = assertThrows(InvalidCredentialsException.class, () -> {
            authService.login(invalidAuthDTO);
        });

        assertEquals("Invalid username or password.", exception.getMessage());
    }

    @Test
    public void testRegister() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUsername("newUser");
        userRegistrationDTO.setPassword("newPassword");
        userRegistrationDTO.setEmail("newUser@example.com");
        userRegistrationDTO.setFirstName("First");
        userRegistrationDTO.setLastName("Last");
        userRegistrationDTO.setRole("USER");

        UserDTO mockedUserDTO = new UserDTO();
        mockedUserDTO.setUsername(userRegistrationDTO.getUsername());

        when(passwordEncoder.encode(userRegistrationDTO.getPassword())).thenReturn("hashedPassword");
        when(userService.save(any(User.class))).thenReturn(mockedUserDTO); // Mock the return value

        UserDTO createdUser = authService.register(userRegistrationDTO);
        assertNotNull(createdUser);
        assertEquals("newUser", createdUser.getUsername());
        verify(userService).save(any(User.class));
    }
}
