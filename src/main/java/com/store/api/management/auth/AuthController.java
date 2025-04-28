package com.store.api.management.auth;

import com.store.api.management.user.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthDTO authDTO) {
        logger.info("Login attempt for username: {}", authDTO.getUsername());
        String token = authService.login(authDTO);
        logger.info("Login successful for username: {}", authDTO.getUsername());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        logger.info("Registration attempt for user: {}", userRegistrationDTO.getUsername());
        UserDTO createdUser = authService.register(userRegistrationDTO);
        logger.info("Registration successful for user: {}", userRegistrationDTO.getUsername());
        return ResponseEntity.status(201).body(createdUser);
    }

}
