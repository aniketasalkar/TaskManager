package com.example.taskmanager.services;

import com.example.taskmanager.models.Project;

import java.util.List;
import java.util.Map;

public interface ProjectService {
    Project createProject(String name, String description, String status, String ownerEmail);
    Project getProjectById(Long id);
    List<Project> getAllProjects();
    Project updateProject(Long id, Map<String, Object> projectDetails);
}
