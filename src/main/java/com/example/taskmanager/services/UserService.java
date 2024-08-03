package com.example.taskmanager.services;

import com.example.taskmanager.Exceptions.EmptyFieldException;
import com.example.taskmanager.Exceptions.InvalidRoleException;
import com.example.taskmanager.Exceptions.InvalidUserNameException;
import com.example.taskmanager.dtos.UserResponseDto;
import com.example.taskmanager.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User addUser(String username, String password, String role, String email, String phone, String firstname,
                 String lastName) throws InvalidUserNameException, EmptyFieldException, InvalidRoleException;

    List<UserResponseDto> getAllUsers();

    User getUserById(String email);
}
