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
}
