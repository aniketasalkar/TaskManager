package com.example.taskmanager.dtos;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import javax.swing.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @NotEmpty(message = "username Required")
    private String username;

    @NotEmpty(message = "password Required")
    @Size(min = 8, message = "Minimum length should be of 8 characters")
    private String password;

    private String role;

    @Nonnull
    @Email
    private String email;

    @Size(min = 10, max = 10, message = "Invalid phone number. Must be 10 digit")
    private String phone;

    @Nonnull
    private String firstname;

    @Nonnull
    private String lastname;
}
