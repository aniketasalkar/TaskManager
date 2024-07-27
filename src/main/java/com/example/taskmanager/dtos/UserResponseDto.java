package com.example.taskmanager.dtos;

import com.example.taskmanager.models.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String username;
    private String role;
    private String email;
    private String phone;
    private Long id;
//    private ResponseStatus status;
}
