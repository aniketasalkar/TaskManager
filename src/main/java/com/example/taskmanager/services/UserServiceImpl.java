package com.example.taskmanager.services;

import com.example.taskmanager.Exceptions.EmptyFieldException;
import com.example.taskmanager.Exceptions.InvalidRoleException;
import com.example.taskmanager.dtos.UserResponseDto;
import com.example.taskmanager.models.User;
import com.example.taskmanager.models.UserRoles;
import com.example.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(String username, String password, String role, String email, String phone,
                        String firstName, String lastName) throws InvalidRoleException {

//        if (username.trim().isEmpty() || password.trim().isEmpty() || email.trim().isEmpty()
//                || firstName.trim().isEmpty() || lastName.trim().isEmpty()) {
//            throw new EmptyFieldException("Empty Field");
//        }

        if (role.isEmpty()) {
            role = "USER";
        }

        if (UserRoles.USER != UserRoles.valueOf(role.toUpperCase()) &&
                UserRoles.ADMIN != UserRoles.valueOf(role.toUpperCase())) {
            throw new InvalidRoleException("Invalid Role");
        }


        Date currentDate = new Date();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(UserRoles.valueOf(role.toUpperCase()));
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
    public User getUserById(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        return user.orElse(null);

    }
}
