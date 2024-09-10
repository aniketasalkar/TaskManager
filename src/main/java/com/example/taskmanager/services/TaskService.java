package com.example.taskmanager.services;

import com.example.taskmanager.exceptions.*;
import com.example.taskmanager.models.Task;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public interface TaskService {

    Task createTask(String title, String description, String priority, Date dueDate, Float points, String type)
            throws EmptyFieldException, InvalidDateException, InvalidTaskTypeException;

    Task getTask(Long id) throws TaskNotFoundException;

    List<Task> getAllTasks();

    Task updateTask(long id, Map<String, Object> taskDetails) throws InvalidFieldException, InvalidDateException;
}
