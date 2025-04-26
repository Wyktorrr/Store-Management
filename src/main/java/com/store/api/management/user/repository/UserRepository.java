package com.store.api.management.user.repository;

import com.store.api.management.user.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
