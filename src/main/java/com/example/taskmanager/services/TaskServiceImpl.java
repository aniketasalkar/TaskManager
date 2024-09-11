package com.example.taskmanager.services;

import com.example.taskmanager.exceptions.*;
import com.example.taskmanager.models.*;
import com.example.taskmanager.repositories.TaskRepository;
import com.example.taskmanager.repositories.UserRepository;
import com.example.taskmanager.utils.StringToEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.taskmanager.utils.StringToEnum.convertStringToEnum;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;

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

        return taskRepository.save(task);
    }

    @Override
    public Task getTask(Long id) throws TaskNotFoundException {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()) {
            throw new TaskNotFoundException("Task not found");
        }

        return taskOptional.get();
    }

    @Override
    public List<Task> getAllTasks() {

        return taskRepository.findAll();

    }

    @Override
    public Task updateTask(long id, Map<String, Object> taskDetails) throws InvalidFieldException, InvalidDateException {
        Task task = getTask(id);

        for (Map.Entry<String, Object> entry : taskDetails.entrySet()) {
            Field field = ReflectionUtils.findField(Task.class, entry.getKey());
            if (field == null) {
                throw new InvalidFieldException("Invalid Field");
            }

            if (field.getName().equals("id")) {
                throw new NonModifiableFieldException("Field cannot be modified");
            }

            field.setAccessible(true);
            if (field.getName().equals("dueDate")) {
                Date dueDate;
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                try {
                    dueDate = format.parse((String) entry.getValue());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if (dueDate.before(new Date())) {
                    throw new InvalidDateException("Due date cannot be before current date");
                }
                ReflectionUtils.setField(field, task, dueDate);
            } else if (field.getName().equals("priority") || field.getName().equals("status") || field.getName().equals("taskType")) {
                try {
                    Class<?> fieldType = field.getType();
                    Enum<?> enumValue = StringToEnum.convertStringToEnum(fieldType.asSubclass(Enum.class), entry.getValue().toString().toUpperCase());
                    ReflectionUtils.setField(field, task, enumValue);
                } catch (Exception e) {
                    throw new InvalidFieldException("Field not found");
                }
            } else if (field.getName().equals("assignee")) {
                try {
//                    User user = userRepository.findByEmail((String) entry.getValue()).get();
                    ReflectionUtils.setField(field, task, userRepository.findByEmail((String) entry.getValue()).
                            orElseThrow(() -> new UserNotFoundException("User not found")));
                } catch (Exception e) {
                    throw new UserNotFoundException("User not found");
                }
            } else {
                ReflectionUtils.setField(field, task, entry.getValue());
            }
        }

        task.setUpdatedAt(new Date());

        return taskRepository.save(task);
    }
}
