package com.example.taskmanager.controllers;

import com.example.taskmanager.dtos.UserRequestDto;
import com.example.taskmanager.dtos.UserResponseDto;
import com.example.taskmanager.models.User;
import com.example.taskmanager.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/taskmanager")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> addUser(@Valid @RequestBody UserRequestDto userRequestDto){

        User user = userService.addUser(userRequestDto.getUsername(), userRequestDto.getPassword(),
                userRequestDto.getRole(), userRequestDto.getEmail(), userRequestDto.getPhone(),
                userRequestDto.getFirstname(), userRequestDto.getLastname());

        return new ResponseEntity<>(from(user), HttpStatus.CREATED);
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){

        List<User> users = userService.getAllUsers();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();

        for (User user : users) {
            userResponseDtos.add(from(user));
        }

        return new ResponseEntity<>(userResponseDtos, HttpStatus.OK);
    }

    @GetMapping("/users/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByIEmail(@PathVariable("email") String email){
        User user = userService.getUserByEmail(email);

        return new ResponseEntity<>(from(user), HttpStatus.OK);
    }

    @GetMapping("/users/username/{username}")
    public ResponseEntity<UserResponseDto> getUserByUsername(@PathVariable("username") String username){
        User user = userService.getUserByUsername(username);

        return new ResponseEntity<>(from(user), HttpStatus.OK);
    }

    @PatchMapping(value = "/users/username/{username}/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("username") String username, @Valid @RequestBody Map<String, Object> userDetails){
        User user = userService.updateUser(username, userDetails);

        return new ResponseEntity<>(from(user), HttpStatus.OK);
    }

    private UserResponseDto from (User user) {
        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhone(user.getPhone());
        userResponseDto.setRole(user.getRole().toString());
        userResponseDto.setId(user.getId());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());

        return userResponseDto;
    }
}
