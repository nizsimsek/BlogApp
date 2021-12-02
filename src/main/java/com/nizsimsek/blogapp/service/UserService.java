package com.nizsimsek.blogapp.service;

import com.nizsimsek.blogapp.dto.UserDto;
import com.nizsimsek.blogapp.dto.converter.UserDtoConverter;
import com.nizsimsek.blogapp.dto.request.CreateUserRequest;
import com.nizsimsek.blogapp.dto.request.UpdateUserRequest;
import com.nizsimsek.blogapp.exception.GeneralNotFoundException;
import com.nizsimsek.blogapp.model.Role;
import com.nizsimsek.blogapp.model.User;
import com.nizsimsek.blogapp.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDtoConverter userDtoConverter;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository,
                       UserDtoConverter userDtoConverter,
                       RoleService roleService,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userDtoConverter = userDtoConverter;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserDto createUser(CreateUserRequest request) {

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                bCryptPasswordEncoder.encode(request.getPassword())
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
                bCryptPasswordEncoder.encode(request.getPassword()),
                user.getRoles(),
                user.getPostList(),
                user.getCommentList(),
                user.getLikedPostList()
        );

        return userDtoConverter.convert(userRepository.save(updatedUser));
    }

    public String deleteUserById(String id) {

        User user = findUserById(id);

        userRepository.delete(user);

        return "User has been deleted with this id : " + id;
    }

    public void addRoleToUser(String username, String roleName) {

        User user = findUserByUsername(username);
        Role role = roleService.findRoleByName(roleName);
        user.getRoles().add(role);

        userRepository.save(user);
    }

    public User findUserByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in database");
        }
        Collection<SimpleGrantedAuthority> roles = new ArrayList<>();
        user.getRoles().forEach(role -> roles.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
    }

    protected User findUserById(String id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new GeneralNotFoundException("User could not found this id : " + id));
    }

    protected List<User> findUsers() {

        return userRepository.findAll();
    }
}
