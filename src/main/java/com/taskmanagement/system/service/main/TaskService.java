package com.taskmanagement.system.service.main;

import com.taskmanagement.system.dto.CommentDto;
import com.taskmanagement.system.dto.TaskDto;
import com.taskmanagement.system.entity.types.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {
    ResponseEntity<?> createTask(TaskDto taskDto);

    ResponseEntity<?> editTask(TaskDto taskDto);

    ResponseEntity<?> getTaskById(Integer id);

    ResponseEntity<Page<TaskDto>> getTaskByUserId(Integer id, Pageable pageable);

    ResponseEntity<Page<TaskDto>> getTaskByPerformerId(Integer id, Pageable pageable);

    ResponseEntity<List<TaskDto>> getAllTasks();

    ResponseEntity<?> deleteTaskById(Integer id);

    ResponseEntity<?> changeStatus(Integer taskId, Status value);

    ResponseEntity<?> appointPerformer(Integer taskId, Integer performerId);

    ResponseEntity<?> addComment(CommentDto commentDto);
}
