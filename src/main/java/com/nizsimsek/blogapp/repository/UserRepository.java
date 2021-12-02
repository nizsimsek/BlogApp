package com.nizsimsek.blogapp.repository;

import com.nizsimsek.blogapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
