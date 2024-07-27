package com.example.taskmanager.controllers;

import com.example.taskmanager.dtos.UserRequestDto;
import com.example.taskmanager.dtos.UserResponseDto;
import com.example.taskmanager.models.User;
import com.example.taskmanager.repositories.UserRepository;
import com.example.taskmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

//import static com.oracle.graal.phases.preciseinline.data.a.k;

//import static com.oracle.graal.compiler.enterprise.c.t;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/api/users")
    public ResponseEntity<UserResponseDto> addUser(@RequestBody UserRequestDto userRequestDto){

        try {
            User user = userService.addUser(userRequestDto.getUsername(), userRequestDto.getPassword(),
                    userRequestDto.getRole(), userRequestDto.getEmail(), userRequestDto.getPhone());

            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setUsername(user.getUsername());
            userResponseDto.setEmail(user.getEmail());
            userResponseDto.setPhone(user.getPhone());
            userResponseDto.setRole(user.getRole().toString());
            userResponseDto.setId(user.getId());


//            userResponseDto.setUsername("abc");
//            userResponseDto.setEmail("abc@gmail.com");
//            userResponseDto.setPhone("1234567890");
//            userResponseDto.setRole("ADMIN");
//            userResponseDto.setId(2L);

            return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
//        UserResponseDto userResponseDto = new UserResponseDto();

        try {
            List<UserResponseDto> userResponseDtos = userService.getAllUsers();

            return ok(userResponseDtos);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/api/users/{email}")
    public ResponseEntity<UserResponseDto> getUserByIEmail(@RequestParam("email") String email){
        try {
            User user = userService.getUserById(email);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setUsername(user.getUsername());
            userResponseDto.setEmail(user.getEmail());
            userResponseDto.setPhone(user.getPhone());
            userResponseDto.setRole(user.getRole().toString());
            userResponseDto.setId(user.getId());


            return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
//            return ok((UserResponseDto) userRepository.findAll());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
