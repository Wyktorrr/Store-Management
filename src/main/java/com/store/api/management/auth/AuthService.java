package com.store.api.management.auth;

import com.store.api.management.exception.InvalidCredentialsException;
import com.store.api.management.user.model.UserDTO;
import com.store.api.management.user.model.domain.User;
import com.store.api.management.user.service.UserService;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

import static com.store.api.management.auth.util.JwtUtil.SECRET_KEY;

@Slf4j
@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
                       UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(AuthDTO authDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(authDTO.getUsername());
            if (userDetails == null) {
                throw new InvalidCredentialsException("Invalid username or password.");
            }
            log.info("User '{}' logged in successfully.", authDTO.getUsername());
            return generateToken(userDetails);
        } catch (Exception e) {
            log.error("Login attempt failed for username: {}", authDTO.getUsername());
            throw new InvalidCredentialsException("Invalid username or password.");
        }
    }

    public UserDTO register(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();

        user.setUsername(userRegistrationDTO.getUsername());

        String hashedPassword = passwordEncoder.encode(userRegistrationDTO.getPassword());
        user.setPassword(hashedPassword);

        user.setEmail(userRegistrationDTO.getEmail());
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setProfilePictureUrl(userRegistrationDTO.getProfilePictureUrl());
        user.setRole(userRegistrationDTO.getRole());
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        UserDTO createdUser = userService.save(user);
        log.info("User '{}' registered successfully.", user.getUsername());
        return createdUser;
    }

    private String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SECRET_KEY)
                .compact();
    }
}
