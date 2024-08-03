package com.example.taskmanager.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseModel{

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
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
    @Column(nullable = false)
    private UserRoles role;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

}
