package com.example.taskmanager.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequestDto {

    @NotEmpty(message = "Name Required")
    private String name;
    private String description;

    private String status;

    private String owner;
}
