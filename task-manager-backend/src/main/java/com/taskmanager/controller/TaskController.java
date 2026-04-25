package com.taskmanager.controller;

import com.taskmanager.dto.CreateTaskRequest;
import com.taskmanager.dto.TaskResponse;
import com.taskmanager.exception.UserException;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.UserRepository;
import com.taskmanager.service.TaskService;
import com.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskService taskService;
//    @Autowired
//    private UserService userService;

    @PostMapping("/tasks")
    public ResponseEntity<?> addTask(@RequestBody CreateTaskRequest task) throws UserException {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        TaskResponse  taskResponse = taskService.createTask(task,email);
        return ResponseEntity.ok(taskResponse);
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getTasks() throws UserException {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        List<TaskResponse> list = taskService.getTasks(email);

        return ResponseEntity.ok(list);
    }
}
