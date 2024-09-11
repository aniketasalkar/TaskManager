package com.example.taskmanager.models;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Projects")
public class Project extends BaseModel{

    @Column(nullable = false)
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskAndProjectStatus status;

    @ManyToOne
    private User owner;

}
