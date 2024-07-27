package com.example.taskmanager.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Projects")
public class Project extends BaseModel{

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskAndProjectStatus status;

    @ManyToOne
    private User owner;

}
