package com.nizsimsek.blogapp.service;

import com.nizsimsek.blogapp.dto.RoleDto;
import com.nizsimsek.blogapp.dto.converter.RoleDtoConverter;
import com.nizsimsek.blogapp.dto.request.CreateRoleRequest;
import com.nizsimsek.blogapp.dto.request.UpdateRoleRequest;
import com.nizsimsek.blogapp.exception.GeneralNotFoundException;
import com.nizsimsek.blogapp.model.Role;
import com.nizsimsek.blogapp.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleDtoConverter roleDtoConverter;

    public RoleService(RoleRepository roleRepository,
                       RoleDtoConverter roleDtoConverter) {
        this.roleRepository = roleRepository;
        this.roleDtoConverter = roleDtoConverter;
    }

    public RoleDto createRole(CreateRoleRequest request) {

        Role role = new Role(
                request.getName()
        );

        return roleDtoConverter.convert(roleRepository.save(role));
    }

    public List<RoleDto> getRoleDtoList() {

        return roleDtoConverter.convertToRoleDtoList(findCategories());
    }

    public RoleDto getRoleById(String id) {

        return roleDtoConverter.convert(findRoleById(id));
    }

    public RoleDto updateRole(String id, UpdateRoleRequest request) {

        Role role = findRoleById(id);

        Role updatedRole = new Role(
                role.getId(),
                request.getName()
        );

        return roleDtoConverter.convert(roleRepository.save(updatedRole));
    }

    public String deleteRoleById(String id) {

        roleRepository.deleteById(id);

        return "Role has been deleted with this id : " + id;
    }

    protected Role findRoleByName(String name) {

        return roleRepository.findByName(name);
    }

    protected Role findRoleById(String id) {

        return roleRepository.findById(id)
                .orElseThrow(() -> new GeneralNotFoundException("Role could not found this id : " + id));
    }

    protected List<Role> findCategories() {

        return roleRepository.findAll();
    }
}
