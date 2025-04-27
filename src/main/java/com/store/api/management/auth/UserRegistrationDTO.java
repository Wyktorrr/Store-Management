package com.store.api.management.auth;

import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String profilePictureUrl;
    private String role;
}
