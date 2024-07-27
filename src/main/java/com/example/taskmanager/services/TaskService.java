package com.example.taskmanager.services;

import com.example.taskmanager.Exceptions.EmptyFieldException;
import com.example.taskmanager.Exceptions.InvalidDateException;
import com.example.taskmanager.Exceptions.InvalidTaskTypeException;
import com.example.taskmanager.models.Task;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface TaskService {

    Task createTask(String title, String description, String priority, Date dueDate, Float points, String type)
            throws EmptyFieldException, InvalidDateException, InvalidTaskTypeException;

    List<Task> getAllTasks();
}
