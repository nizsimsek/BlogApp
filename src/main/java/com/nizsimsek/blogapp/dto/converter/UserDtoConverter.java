package com.nizsimsek.blogapp.dto.converter;

import com.nizsimsek.blogapp.dto.UserDto;
import com.nizsimsek.blogapp.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoConverter {

    public UserDto convert(User user) {

        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword()
        );
    }

    public List<UserDto> convertToUserDtoList(List<User> userList) {

        return userList.stream().map(this::convert).collect(Collectors.toList());
    }
}