package com.example.taskmanager.dtos;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDto {
    @NotEmpty(message = "Title Required")
    private String title;
    private String description;
    private String priority;
    private Date dueDate;
    private float points;
    private String type;
//    private long projectId;

}
