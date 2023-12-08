package com.taskmanagement.system.controller;


import com.taskmanagement.system.dto.CommentDto;
import com.taskmanagement.system.dto.TaskDto;
import com.taskmanagement.system.entity.types.Status;
import com.taskmanagement.system.service.main.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<?> createTask(
            @RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @PutMapping
    public ResponseEntity<?> editTask(@RequestBody TaskDto taskDto) {
        return taskService.editTask(taskDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Integer id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/by-author-id/{id}")
    public ResponseEntity<Page<TaskDto>> getTaskByUserId(@PathVariable Integer id,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size
                                             ) {
        return taskService.getTaskByUserId(id, PageRequest.of(page, size));
    }

    @GetMapping("/by-performer-id/{id}")
    public ResponseEntity<Page<TaskDto>> getTaskByPerformerId(@PathVariable Integer id,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        return taskService.getTaskByPerformerId(id, PageRequest.of(page, size));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        return taskService.getAllTasks();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Integer id) {
        return taskService.deleteTaskById(id);
    }

    @PatchMapping("/status")
    public ResponseEntity<?> changeStatus(@RequestParam Integer taskId, @RequestParam Status value) {
        return taskService.changeStatus(taskId, value);
    }


    @PatchMapping("/appoint")
    public ResponseEntity<?> appoint(@RequestParam Integer taskId, @RequestParam Integer performerId) {
        return taskService.appointPerformer(taskId, performerId);
    }


    @PostMapping("/comment")
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto) {
        return taskService.addComment(commentDto);
    }


}
