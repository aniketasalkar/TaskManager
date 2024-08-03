package com.example.taskmanager.services;

import com.example.taskmanager.exceptions.EmptyFieldException;
import com.example.taskmanager.exceptions.InvalidDateException;
import com.example.taskmanager.exceptions.InvalidTaskTypeException;
import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.TaskAndProjectStatus;
import com.example.taskmanager.models.TaskPriority;
import com.example.taskmanager.models.TaskType;
import com.example.taskmanager.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Override
    public Task createTask(String title, String description, String priority, Date dueDate, Float points, String type)
            throws EmptyFieldException, InvalidDateException, InvalidTaskTypeException {

        if (title == null || description == null || priority == null || dueDate == null || points == null || type == null) {
            throw new EmptyFieldException("Empty Fields");
        }

        if (TaskType.valueOf(type.toUpperCase()) != TaskType.TASK &&
                TaskType.valueOf(type.toUpperCase()) != TaskType.BUG &&
                TaskType.valueOf(type.toUpperCase()) != TaskType.INCIDENT) {
            throw new InvalidTaskTypeException("Invalid Task Type");
        }

        Date currentDate = new Date();
        if (dueDate.before(currentDate)) {
            throw new InvalidDateException("Due date cannot be before current date");
        }

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setPriority(TaskPriority.valueOf(priority.toUpperCase()));
        task.setDueDate(dueDate);
        task.setPoints(points);
        task.setTaskType(TaskType.valueOf(type.toUpperCase()));
        task.setStatus(TaskAndProjectStatus.NOT_STARTED);
        task.setCreatedAt(currentDate);
        task.setUpdatedAt(currentDate);

//        task.setTitle("title");
//        task.setDescription("description");
//        task.setPriority(TaskPriority.HIGH);
//        task.setDueDate(dueDate);
//        task.setPoints(2);
//        task.setTaskType(TaskType.BUG);
//        task.setStatus(TaskAndProjectStatus.NOT_STARTED);
//        task.setCreatedAt(currentDate);
//        task.setUpdatedAt(currentDate);

        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTasks() {

        return taskRepository.findAll();

    }
}
