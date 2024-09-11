package com.example.taskmanager.services;

import com.example.taskmanager.models.Project;

import java.util.List;

public interface ProjectService {
    Project createProject(String name, String description, String status, String ownerEmail);
    Project getProjectById(Long id);
    List<Project> getAllProjects();
}
