package com.example.taskmanager.services;

import com.example.taskmanager.exceptions.*;
import com.example.taskmanager.dtos.UserResponseDto;
import com.example.taskmanager.models.User;
import com.example.taskmanager.models.UserRoles;
import com.example.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(String username, String password, String role, String email, String phone,
                        String firstName, String lastName) {

        if (role.isEmpty()) {
            role = "USER";
        }

        Date currentDate = new Date();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        try {
            user.setRole(UserRoles.valueOf(role.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new InvalidRoleException("Invalid Role");
        }
        user.setEmail(email);
        user.setPhone(phone);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCreatedAt(currentDate);
        user.setUpdatedAt(currentDate);

        return userRepository.save(user);

    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();

        for (User user : users) {
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setId(user.getId());
            userResponseDto.setUsername(user.getUsername());
            userResponseDto.setEmail(user.getEmail());
            userResponseDto.setRole(user.getRole().toString());
            userResponseDto.setPhone(user.getPhone());
            userResponseDto.setFirstName(user.getFirstName());
            userResponseDto.setLastName(user.getLastName());

            userResponseDtos.add(userResponseDto);
        }

        return userResponseDtos;
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        return user.get();

    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        return user.get();
    }

    @Override
    public User updateUser(String username, Map<String, Object> userDetails) {
        User user = getUserByUsername(username);

        userDetails.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(User.class, key);
            if (field == null) {
                throw new InvalidFieldException("Invalid Field");
            }
            if (field.getName().equals("firstName") || field.getName().equals("lastName") || field.getName().equals("id")) {
                throw new NonModifiableFieldException("Field firstName or lastName or Id are not modifiable");
            }
            field.setAccessible(true);
            ReflectionUtils.setField(field, user, (String) value);
        });

        Date currentDate = new Date();
        user.setUpdatedAt(currentDate);

        return userRepository.save(user);
    }

}
