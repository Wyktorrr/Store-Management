package com.store.api.management.user.model.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String profilePictureUrl;
    private String role;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}