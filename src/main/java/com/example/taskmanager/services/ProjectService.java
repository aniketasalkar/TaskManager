package com.example.taskmanager.services;

import com.example.taskmanager.models.Project;

public interface ProjectService {
    Project createProject(String name, String description, String status, String ownerEmail);
    Project getProjectById(Long id);
}
