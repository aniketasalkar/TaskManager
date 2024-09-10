package com.example.taskmanager.controllers;

import com.example.taskmanager.dtos.TaskPatchRequestDto;
import com.example.taskmanager.dtos.TaskRequestDto;
import com.example.taskmanager.dtos.TaskResponseDto;
import com.example.taskmanager.models.Task;
import com.example.taskmanager.services.TaskService;
import com.example.taskmanager.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/tasks")
    public ResponseEntity<TaskResponseDto> addTask(@RequestBody TaskRequestDto taskRequestDto) {

        try {
            Task task  = taskService.createTask(taskRequestDto.getTitle(), taskRequestDto.getDescription(),
                    taskRequestDto.getPriority(), taskRequestDto.getDueDate(), taskRequestDto.getPoints(),
                    taskRequestDto.getType());

            TaskResponseDto taskResponseDto = new TaskResponseDto();
            taskResponseDto.setId(task.getId());
            taskResponseDto.setTitle(task.getTitle());
            taskResponseDto.setDescription(task.getDescription());
            taskResponseDto.setPriority(String.valueOf(task.getPriority()));
            taskResponseDto.setDueDate(task.getDueDate());
            taskResponseDto.setPoints(task.getPoints());
            taskResponseDto.setTaskType(String.valueOf(task.getTaskType()));
            taskResponseDto.setAssignee(task.getAssignee() != null ? task.getAssignee().getEmail() : "null");
            taskResponseDto.setStatus(String.valueOf(task.getStatus()));

            return new ResponseEntity<>(taskResponseDto, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
        try {
            List<Task> tasks = taskService.getAllTasks();
            List<TaskResponseDto> taskResponseDtos = new ArrayList<>();
            for (Task task : tasks) {
                TaskResponseDto taskResponseDto = new TaskResponseDto();
                taskResponseDto.setId(task.getId());
                taskResponseDto.setTitle(task.getTitle());
                taskResponseDto.setDescription(task.getDescription());
                taskResponseDto.setPriority(String.valueOf(task.getPriority()));
                taskResponseDto.setDueDate(task.getDueDate());
                taskResponseDto.setPoints(task.getPoints());
                taskResponseDto.setTaskType(String.valueOf(task.getTaskType()));
                taskResponseDto.setAssignee(task.getAssignee() != null ? task.getAssignee().getEmail() : "null");
                taskResponseDto.setStatus(String.valueOf(task.getStatus()));
                taskResponseDtos.add(taskResponseDto);
            }

            return new ResponseEntity<>(taskResponseDtos, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/tasks/id/{id}")
    public ResponseEntity<TaskResponseDto> getAllTasks(@PathVariable Long id) {
        Task task = taskService.getTask(id);

        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setId(task.getId());
        taskResponseDto.setTitle(task.getTitle());
        taskResponseDto.setDescription(task.getDescription());
        taskResponseDto.setPriority(String.valueOf(task.getPriority()));
        taskResponseDto.setDueDate(task.getDueDate());
        taskResponseDto.setPoints(task.getPoints());
        taskResponseDto.setTaskType(String.valueOf(task.getTaskType()));
        taskResponseDto.setAssignee(task.getAssignee() != null ? task.getAssignee().getEmail() : "null");
        taskResponseDto.setStatus(String.valueOf(task.getStatus()));

        return new ResponseEntity<>(taskResponseDto, HttpStatus.OK);

    }

    @PatchMapping(value = "tasks/id/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable("id") long id, @Valid @RequestBody Map<String, Object> taskDetails) {
        Task task = this.taskService.updateTask(id, taskDetails);

        TaskResponseDto taskResponseDto = new TaskResponseDto();

        try {
            taskResponseDto.setId(task.getId());
            taskResponseDto.setTitle(task.getTitle());
            taskResponseDto.setDescription(task.getDescription());
            taskResponseDto.setPriority(String.valueOf(task.getPriority()));
            taskResponseDto.setDueDate(task.getDueDate());
            taskResponseDto.setPoints(task.getPoints());
            taskResponseDto.setTaskType(String.valueOf(task.getTaskType()));
            taskResponseDto.setAssignee(task.getAssignee() != null ? task.getAssignee().getEmail() : "null");
            taskResponseDto.setStatus(String.valueOf(task.getStatus()));
            return new ResponseEntity<>(taskResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
