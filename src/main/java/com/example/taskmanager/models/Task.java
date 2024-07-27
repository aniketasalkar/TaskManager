package com.example.taskmanager.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Tasks")
public class Task extends BaseModel {
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    private Date dueDate;
    private float points;

    @Enumerated(EnumType.STRING)
    private TaskAndProjectStatus status;

    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @ManyToOne
    private User assignee;
    private float point;
}
