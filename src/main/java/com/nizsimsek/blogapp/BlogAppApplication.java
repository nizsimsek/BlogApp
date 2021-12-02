package com.nizsimsek.blogapp;

import com.nizsimsek.blogapp.dto.request.CreateRoleRequest;
import com.nizsimsek.blogapp.dto.request.CreateUserRequest;
import com.nizsimsek.blogapp.service.RoleService;
import com.nizsimsek.blogapp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BlogAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogAppApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
/*
    @Bean
    CommandLineRunner runner(UserService userService, RoleService roleService) {
        return args -> {
            roleService.createRole(new CreateRoleRequest("ROLE_USER"));
            roleService.createRole(new CreateRoleRequest("ROLE_MANAGER"));
            roleService.createRole(new CreateRoleRequest("ROLE_ADMIN"));
            roleService.createRole(new CreateRoleRequest("ROLE_SUPER_ADMIN"));

            userService.createUser(
                    new CreateUserRequest("ali", "ali@email.com", "Ali",
                            "Günay", "password"));
            userService.createUser(
                    new CreateUserRequest("mehmet", "mehmet@email.com", "Mehmet",
                            "Günay", "password"));
            userService.createUser(
                    new CreateUserRequest("admin", "admin@email.com", "Nizamettin",
                            "Şimşek", "password"));
            userService.createUser(
                    new CreateUserRequest("deniz", "deniz@email.com", "Deniz",
                            "Aksu", "password"));

            userService.addRoleToUser("ali", "ROLE_USER");
            userService.addRoleToUser("mehmet", "ROLE_USER");
            userService.addRoleToUser("mehmet", "ROLE_MANAGER");
            userService.addRoleToUser("admin", "ROLE_ADMIN");
            userService.addRoleToUser("admin", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("deniz", "ROLE_ADMIN");
            userService.addRoleToUser("deniz", "ROLE_SUPER_ADMIN");
        };
    }*/
}
