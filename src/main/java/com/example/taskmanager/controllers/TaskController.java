package com.example.taskmanager.controllers;

import com.example.taskmanager.dtos.TaskRequestDto;
import com.example.taskmanager.dtos.TaskResponseDto;
import com.example.taskmanager.models.Task;
import com.example.taskmanager.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    TaskService taskService;

    @RequestMapping("/tasks")
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
            taskResponseDto.setAssignee(String.valueOf(task.getAssignee()));
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
                taskResponseDto.setAssignee(String.valueOf(task.getAssignee()));
                taskResponseDto.setStatus(String.valueOf(task.getStatus()));
                taskResponseDtos.add(taskResponseDto);
            }

            return new ResponseEntity<>(taskResponseDtos, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
