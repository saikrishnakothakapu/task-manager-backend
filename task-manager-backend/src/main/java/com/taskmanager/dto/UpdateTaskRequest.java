package com.taskmanager.dto;

import com.taskmanager.entity.enums.TaskPriority;
import com.taskmanager.entity.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateTaskRequest {

    private String title;
    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private LocalDateTime dueDate;


}
