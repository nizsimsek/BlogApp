package com.nizsimsek.blogapp.service;

import com.nizsimsek.blogapp.dto.UserDto;
import com.nizsimsek.blogapp.dto.converter.UserDtoConverter;
import com.nizsimsek.blogapp.dto.request.CreateUserRequest;
import com.nizsimsek.blogapp.dto.request.UpdateUserRequest;
import com.nizsimsek.blogapp.exception.GeneralNotFoundException;
import com.nizsimsek.blogapp.model.User;
import com.nizsimsek.blogapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoConverter userDtoConverter;

    public UserService(UserRepository userRepository,
                       UserDtoConverter userDtoConverter) {
        this.userRepository = userRepository;
        this.userDtoConverter = userDtoConverter;
    }

    public UserDto createUser(CreateUserRequest request) {

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getPassword()
        );

        return userDtoConverter.convert(userRepository.save(user));
    }

    public List<UserDto> getUserDtoList() {

        return userDtoConverter.convertToUserDtoList(findUsers());
    }

    public UserDto getUserById(String id) {

        return userDtoConverter.convert(findUserById(id));
    }

    public UserDto updateUser(String id, UpdateUserRequest request) {

        User user = findUserById(id);

        User updatedUser = new User(
                user.getId(),
                request.getUsername(),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getPassword(),
                user.getPostList(),
                user.getCommentList()
        );

        return userDtoConverter.convert(userRepository.save(updatedUser));
    }

    public String deleteUserById(String id) {

        User user = findUserById(id);

        userRepository.delete(user);

        return "User has been deleted with this id : " + id;
    }

    protected User findUserById(String id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new GeneralNotFoundException("User could not found this id : " + id));
    }

    protected List<User> findUsers() {

        return userRepository.findAll();
    }
}
