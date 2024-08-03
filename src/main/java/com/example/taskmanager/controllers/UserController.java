package com.example.taskmanager.controllers;

import com.example.taskmanager.dtos.UserRequestDto;
import com.example.taskmanager.dtos.UserResponseDto;
import com.example.taskmanager.models.User;
import com.example.taskmanager.repositories.UserRepository;
import com.example.taskmanager.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    private ValidationHandler validationHandler;

    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> addUser(@Valid @RequestBody UserRequestDto userRequestDto){

        User user = userService.addUser(userRequestDto.getUsername(), userRequestDto.getPassword(),
                userRequestDto.getRole(), userRequestDto.getEmail(), userRequestDto.getPhone(),
                userRequestDto.getFirstname(), userRequestDto.getLastname());

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhone(user.getPhone());
        userResponseDto.setRole(user.getRole().toString());
        userResponseDto.setId(user.getId());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){

        List<UserResponseDto> userResponseDtos = userService.getAllUsers();

        return ok(userResponseDtos);

    }

    @GetMapping("/users/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByIEmail(@PathVariable("email") String email){
        User user = userService.getUserByEmail(email);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhone(user.getPhone());
        userResponseDto.setRole(user.getRole().toString());
        userResponseDto.setId(user.getId());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());


        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @GetMapping("/users/username/{username}")
    public ResponseEntity<UserResponseDto> getUserByUsername(@PathVariable("username") String username){
        User user = userService.getUserByUsername(username);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhone(user.getPhone());
        userResponseDto.setRole(user.getRole().toString());
        userResponseDto.setId(user.getId());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @PatchMapping(value = "/users/username/{username}/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("username") String username, @Valid @RequestBody Map<String, Object> userDetails){
        User user = userService.updateUser(username, userDetails);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhone(user.getPhone());
        userResponseDto.setRole(user.getRole().toString());
        userResponseDto.setId(user.getId());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }
}
