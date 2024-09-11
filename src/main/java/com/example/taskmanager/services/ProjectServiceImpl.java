package com.example.taskmanager.services;

import com.example.taskmanager.exceptions.*;
import com.example.taskmanager.models.Project;
import com.example.taskmanager.models.TaskAndProjectStatus;
import com.example.taskmanager.models.User;
import com.example.taskmanager.repositories.ProjectRepository;
import com.example.taskmanager.repositories.UserRepository;
import com.example.taskmanager.utils.StringToEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

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

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project updateProject(Long id, Map<String, Object> projectDetails) {
        Project project = getProjectById(id);

        for (Map.Entry<String, Object> entry : projectDetails.entrySet()) {
            Field field = ReflectionUtils.findField(Project.class, entry.getKey());

            if (field == null || entry.getValue().toString().trim().isEmpty()) {
                throw new InvalidFieldException("Invalid Field or Data");
            }

            if (field.getName().equals("id")) {
                throw new NonModifiableFieldException("Field cannot be modified");
            }

            field.setAccessible(true);

            if (field.getName().equals("TaskAndProjectStatus")) {
                try {
                    Class<?> fieldType = field.getType();
                    Enum<?> enumValue = StringToEnum.convertStringToEnum(fieldType.asSubclass(Enum.class), entry.getValue().toString().toUpperCase());
                    ReflectionUtils.setField(field, project, enumValue);
                } catch (Exception e) {
                    throw new InvalidFieldException("Field not found");
                }
            } else if (field.getName().equals("owner")) {
                ReflectionUtils.setField(field, project, userRepository.findByEmail((String) entry.getValue()).
                        orElseThrow(() -> new UserNotFoundException("User not found")));
            } else {
                ReflectionUtils.setField(field, project, entry.getValue());
            }

        }

        project.setUpdatedAt(new Date());

        return projectRepository.save(project);
    }
}
