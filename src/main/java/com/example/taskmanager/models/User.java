package com.example.taskmanager.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseModel{

    private String username;
    private String password;

//    BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    {
//        bCryptPasswordEncoder = new BCryptPasswordEncoder();
//    }
//
//    String bCryptedPassword = bCryptPasswordEncoder.encode("password");
//    boolean passwordIsValid = bCryptPasswordEncoder.matches("password", bCryptedPassword);

    @Enumerated(EnumType.STRING)
    private UserRoles role;

    private String email;
    private String phone;

}
