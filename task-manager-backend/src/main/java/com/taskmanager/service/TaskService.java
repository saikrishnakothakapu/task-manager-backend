package com.taskmanager.service;

import com.taskmanager.dto.CreateTaskRequest;
import com.taskmanager.dto.TaskResponse;
import com.taskmanager.dto.UpdateTaskRequest;
import com.taskmanager.exception.UserException;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(CreateTaskRequest request, String userEmail) throws UserException;

    List<TaskResponse> getTasks(String userEmail) throws UserException;

    TaskResponse updateTask(Long taskId,UpdateTaskRequest request, String userEmail);

    void deleteTask(Long taskId, String userEmail);

}
