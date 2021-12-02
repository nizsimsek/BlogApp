package com.nizsimsek.blogapp.repository;

import com.nizsimsek.blogapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByName(String name);
}
