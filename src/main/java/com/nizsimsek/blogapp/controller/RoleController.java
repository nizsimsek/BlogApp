package com.nizsimsek.blogapp.controller;

import com.nizsimsek.blogapp.dto.RoleDto;
import com.nizsimsek.blogapp.dto.request.CreateRoleRequest;
import com.nizsimsek.blogapp.dto.request.UpdateRoleRequest;
import com.nizsimsek.blogapp.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@Valid @RequestBody CreateRoleRequest request) {
        return ResponseEntity.ok(roleService.createRole(request));
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> getRoleList() {
        return ResponseEntity.ok(roleService.getRoleDtoList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable String id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable String id, @Valid @RequestBody UpdateRoleRequest request) {
        return ResponseEntity.ok(roleService.updateRole(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoleById(@PathVariable String id) {
        return ResponseEntity.ok(roleService.deleteRoleById(id));
    }
}
