package com.example.taskmanager.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserPatchRequestDto {

    private String username;

    @Size(min = 8, message = "Minimum length should be of 8 characters")
    private String password;

    private String role;

    @Email(message = "Invalid Email")
    private String email;

    @Size(min = 10, max = 10, message = "Invalid phone number. Must be 10 digit")
    private String phone;
}
