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
@RequestMapping("/api")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/projects")
    public ResponseEntity<ProjectResponseDto> createProject(@RequestBody @Valid ProjectRequestDto projectRequestDto) {
        Project project = projectService.createProject(projectRequestDto.getName().strip(), projectRequestDto.getDescription(),
                projectRequestDto.getStatus().strip(), projectRequestDto.getOwner().strip());

        ProjectResponseDto projectResponseDto = new ProjectResponseDto();
        projectResponseDto.setId(project.getId());
        projectResponseDto.setName(project.getName());
        projectResponseDto.setDescription(project.getDescription());
        projectResponseDto.setStatus(project.getStatus().toString());
        projectResponseDto.setOwner(project.getOwner().getEmail());

        return new ResponseEntity<>(projectResponseDto, HttpStatus.CREATED);

    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<ProjectResponseDto> getProjectById(@PathVariable("id") Long id) {
        Project project = projectService.getProjectById(id);

        ProjectResponseDto projectResponseDto = new ProjectResponseDto();

        projectResponseDto.setId(project.getId());
        projectResponseDto.setName(project.getName());
        projectResponseDto.setDescription(project.getDescription());
        projectResponseDto.setStatus(project.getStatus().toString());
        projectResponseDto.setOwner(project.getOwner().getEmail());

        return new ResponseEntity<>(projectResponseDto, HttpStatus.OK);

    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectResponseDto>> getAllProjects() {
        List<ProjectResponseDto> responseDtos = new ArrayList<>();
        List<Project> projects = projectService.getAllProjects();

        for (Project project : projects) {
            ProjectResponseDto projectResponseDto = new ProjectResponseDto();

            projectResponseDto.setId(project.getId());
            projectResponseDto.setName(project.getName());
            projectResponseDto.setDescription(project.getDescription());
            projectResponseDto.setStatus(project.getStatus().toString());
            projectResponseDto.setOwner(project.getOwner().getEmail());

            responseDtos.add(projectResponseDto);
        }

        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @PatchMapping("/projects/{id}")
    public ResponseEntity<ProjectResponseDto> updateProject(@PathVariable("id") Long id, @Valid @RequestBody Map<String, Object> projectDetails) {
        Project project = projectService.updateProject(id, projectDetails);

        ProjectResponseDto projectResponseDto = new ProjectResponseDto();
        projectResponseDto.setId(id);
        projectResponseDto.setName(project.getName());
        projectResponseDto.setDescription(project.getDescription());
        projectResponseDto.setStatus(String.valueOf(project.getStatus()));
        projectResponseDto.setOwner(project.getOwner() != null ? project.getOwner().getEmail() : null);

        return new ResponseEntity<>(projectResponseDto, HttpStatus.OK);
    }
}
