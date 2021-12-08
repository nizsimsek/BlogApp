package com.nizsimsek.blogapp.controller;

import com.nizsimsek.blogapp.dto.UserDto;
import com.nizsimsek.blogapp.dto.request.CreateUserRequest;
import com.nizsimsek.blogapp.dto.request.RoleToUserRequest;
import com.nizsimsek.blogapp.dto.request.UpdateUserRequest;
import com.nizsimsek.blogapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PostMapping("/add-role")
    public ResponseEntity<UserDto> addRoleToUser(@Valid @RequestBody RoleToUserRequest request) {
        userService.addRoleToUser(request.getUsername(), request.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUserList() {
        return ResponseEntity.ok(userService.getUserDtoList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.deleteUserById(id));
    }
}
