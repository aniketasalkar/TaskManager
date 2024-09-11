package com.example.taskmanager.dtos;

import com.example.taskmanager.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectResponseDto {

    private Long id;
    private String name;
    private String description;

    private String status;

    private String owner;
}
