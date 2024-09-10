package com.example.taskmanager.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TaskPatchRequestDto {
    private String title;
    private String description;
    private String priority;
    private Date dueDate;
    private float points;
    private String type;
}
