package com.taskmanager.dto;

import com.taskmanager.entity.enums.TaskPriority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequest {

    private String title;
    private String description;
    private TaskPriority priority;
    private LocalDateTime dueDate;
}
