package com.example.taskmanager.controllers;

import com.example.taskmanager.dtos.ProjectRequestDto;
import com.example.taskmanager.dtos.ProjectResponseDto;
import com.example.taskmanager.models.Project;
import com.example.taskmanager.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/taskmanager")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/projects")
    public ResponseEntity<ProjectResponseDto> createProject(@RequestBody @Valid ProjectRequestDto projectRequestDto) {
        Project project = projectService.createProject(projectRequestDto.getName().strip(), projectRequestDto.getDescription(),
                projectRequestDto.getStatus().strip(), projectRequestDto.getOwner().strip());

        return new ResponseEntity<>(from(project), HttpStatus.CREATED);

    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<ProjectResponseDto> getProjectById(@PathVariable("id") Long id) {
        Project project = projectService.getProjectById(id);

        return new ResponseEntity<>(from(project), HttpStatus.OK);

    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectResponseDto>> getAllProjects() {
        List<ProjectResponseDto> responseDtos = new ArrayList<>();
        List<Project> projects = projectService.getAllProjects();

        for (Project project : projects) {
            responseDtos.add(from(project));
        }

        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @PatchMapping("/projects/{id}")
    public ResponseEntity<ProjectResponseDto> updateProject(@PathVariable("id") Long id, @Valid @RequestBody Map<String, Object> projectDetails) {
        Project project = projectService.updateProject(id, projectDetails);

        return new ResponseEntity<>(from(project), HttpStatus.OK);
    }

    private ProjectResponseDto from (Project project) {
        ProjectResponseDto projectResponseDto = new ProjectResponseDto();

        projectResponseDto.setId(project.getId());
        projectResponseDto.setName(project.getName());
        projectResponseDto.setDescription(project.getDescription());
        projectResponseDto.setStatus(String.valueOf(project.getStatus()));
        projectResponseDto.setOwner(project.getOwner() != null ? project.getOwner().getEmail() : null);

        return projectResponseDto;
    }
}
