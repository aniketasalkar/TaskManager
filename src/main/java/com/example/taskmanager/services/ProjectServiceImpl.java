package com.example.taskmanager.services;

import com.example.taskmanager.exceptions.InvalidTaskProjectStatusException;
import com.example.taskmanager.exceptions.ProjectNotFoundException;
import com.example.taskmanager.models.Project;
import com.example.taskmanager.models.TaskAndProjectStatus;
import com.example.taskmanager.repositories.ProjectRepository;
import com.example.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserService userService;

    @Override
    public Project createProject(String name, String description, String status, String ownerEmail) {
        Project project = new Project();

        Date currentDate = new Date();

        if (status.isEmpty()) {
            status = "NOT_STARTED";
        }

        project.setName(name);
        project.setDescription(description);
        try {
            project.setStatus(TaskAndProjectStatus.valueOf(status.toUpperCase()));
        } catch (InvalidTaskProjectStatusException e) {
            throw new InvalidTaskProjectStatusException("Invalid project status");
        }
        project.setOwner(userService.getUserByEmail(ownerEmail));
        project.setCreatedAt(currentDate);
        project.setUpdatedAt(currentDate);

        return projectRepository.save(project);
    }

    @Override
    public Project getProjectById(Long id) {
        Optional<Project> project = projectRepository.findById(id);

        if (!project.isPresent()) {
            throw new ProjectNotFoundException("Project not found");
        }

        return project.get();
    }
}
