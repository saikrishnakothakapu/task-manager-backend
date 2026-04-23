package com.taskmanager.service;

import com.taskmanager.dto.CreateTaskRequest;
import com.taskmanager.dto.TaskResponse;
import com.taskmanager.dto.UpdateTaskRequest;
import com.taskmanager.entity.Task;
import com.taskmanager.entity.User;
import com.taskmanager.entity.enums.TaskStatus;
import com.taskmanager.exception.UserException;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    @Autowired
    private final TaskRepository taskRepository;

    @Autowired
    private final UserRepository userRepository;

    @Override
    public TaskResponse createTask(CreateTaskRequest request, String userEmail) throws UserException {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(()-> new UserException("User not found"));



        if(request.getDueDate().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Due date is before now");
        }

        Task newTask = new Task();
        newTask.setTitle(request.getTitle());
        newTask.setDescription(request.getDescription());
        newTask.setTaskPriority(request.getPriority());
        newTask.setStatus(TaskStatus.TODO);
        newTask.setUser(user);
        newTask.setDueDate(request.getDueDate());
        Task savedTask = taskRepository.save(newTask);

        return mapToResponse(savedTask);
    }

    @Override
    public List<TaskResponse> getTasks(String userEmail) throws UserException {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(()-> new UserException("User not found"));

        return taskRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse updateTask(Long taskId, UpdateTaskRequest request, String userEmail) {


        Task task = taskRepository.findById(taskId)
                .orElseThrow(()->new RuntimeException("Task not found"));

        if(!task.getUser().getEmail().equals(userEmail)){
            throw new RuntimeException("Unauthorized");
        }
        if(request.getDueDate().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Due date is before now");
        }

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setTaskPriority(request.getPriority());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());

        Task updatedTask = taskRepository.save(task);
        return mapToResponse(updatedTask);
    }

    @Override
    public void deleteTask(Long taskId, String userEmail) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(()->new RuntimeException("Task not found"));
        if(!task.getUser().getEmail().equals(userEmail)){
            throw new RuntimeException("Unauthorized");
        }

        taskRepository.delete(task);

    }

    private TaskResponse mapToResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setPriority(task.getTaskPriority());
        response.setDueDate(task.getDueDate());
        response.setCreatedAt(task.getCreatedAt());
        return response;
    }
}
