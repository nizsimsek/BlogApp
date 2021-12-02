package com.nizsimsek.blogapp.dto.converter;

import com.nizsimsek.blogapp.dto.RoleDto;
import com.nizsimsek.blogapp.model.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleDtoConverter {

    public RoleDto convert(Role role) {

        return new RoleDto(
                role.getId(),
                role.getName()
        );
    }

    public List<RoleDto> convertToRoleDtoList(List<Role> roleList) {

        return roleList.stream().map(this::convert).collect(Collectors.toList());
    }
}
