package com.taskmanagement.system.service.impl;

import com.taskmanagement.system.dto.CommentDto;
import com.taskmanagement.system.dto.TaskDto;
import com.taskmanagement.system.entity.Comment;
import com.taskmanagement.system.entity.Task;
import com.taskmanagement.system.entity.types.Status;
import com.taskmanagement.system.mapper.TaskMapper;
import com.taskmanagement.system.repository.CommentRepository;
import com.taskmanagement.system.repository.TaskRepository;
import com.taskmanagement.system.repository.UserRepository;
import com.taskmanagement.system.service.main.TaskService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.taskmanagement.system.helper.ResponseEntityHelper.ERROR_RESPONSE;
import static com.taskmanagement.system.helper.ResponseEntityHelper.SUCCESS_RESPONSE;
import static com.taskmanagement.system.helper.ResponseMessageHelper.error_while_operation;
import static com.taskmanagement.system.helper.ResponseMessageHelper.incorrect_id;


@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    public static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final TaskMapper taskMapper;


    @Override
    public ResponseEntity<?> createTask(TaskDto taskDto) {
        try {
            if(!userRepository.existsById(taskDto.getAuthorId()))
                return ERROR_RESPONSE(
                        incorrect_id("user", taskDto.getAuthorId()),
                        500
                );

            Task task = taskMapper.toEntity(taskDto);
            taskRepository.save(task);

            return SUCCESS_RESPONSE("Successfully created!");
        } catch (Exception e) {
            logger.error("Error while creating task!");
            return ERROR_RESPONSE(
                    error_while_operation("task", "creating"),
                    500
            );
        }
    }

    @Override
    public ResponseEntity<?> editTask(TaskDto taskDto) {
        try {
            Optional<Task> optional = taskRepository.findById(taskDto.getId());
            if(optional.isEmpty())
                return ERROR_RESPONSE(
                        incorrect_id("task", taskDto.getId()),
                        500
                );

            Task task = optional.get();
            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());
            task.setPerformerId(taskDto.getPerformerId());
            task.setPriority(taskDto.getPriority());
            taskRepository.save(task);

            return SUCCESS_RESPONSE("Successfully updated!");
        } catch (Exception e) {
            logger.error("Error while updating task!");
            return ERROR_RESPONSE(
                    error_while_operation("task", "updating"),
                    500
            );
        }
    }

    @Override
    public ResponseEntity<?> getTaskById(Integer id) {
        Optional<Task> optional = taskRepository.findById(id);
        if(optional.isEmpty())
            return ERROR_RESPONSE(
                    incorrect_id("task", id),
                    500
            );

        TaskDto taskDto = optional.map(taskMapper::toDto).get();
        return SUCCESS_RESPONSE(taskDto);
    }

    @Override
    public ResponseEntity<Page<TaskDto>> getTaskByUserId(Integer id, Pageable pageable) {
        Page<TaskDto> tasks = taskRepository.findByAuthorId(id, pageable)
                .map(taskMapper::toDto);
        return SUCCESS_RESPONSE(tasks);
    }

    @Override
    public ResponseEntity<Page<TaskDto>> getTaskByPerformerId(Integer id, Pageable pageable) {
        Page<TaskDto> tasks = taskRepository.findByPerformerId(id, pageable)
                .map(taskMapper::toDto);
        return SUCCESS_RESPONSE(tasks);
    }

    @Override
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<TaskDto> tasks = taskRepository.findAll().stream()
                .map(taskMapper::toDto).toList();
        return SUCCESS_RESPONSE(tasks);
    }

    @Override
    public ResponseEntity<?> deleteTaskById(Integer id) {
        try {
            taskRepository.deleteById(id);
            return SUCCESS_RESPONSE("Successfully deleted!");
        }catch (Exception e) {
            logger.error("Error while deleting task!");
            return ERROR_RESPONSE(
                    error_while_operation("task", "deleting"),
                    500
            );
        }
    }

    @Override
    public ResponseEntity<?> changeStatus(Integer taskId, Status value) {
        try {
            Optional<Task> optional = taskRepository.findById(taskId);
            if(optional.isEmpty())
                return ERROR_RESPONSE(
                        incorrect_id("task", taskId),
                        404
                );
            Task task = optional.get();
            task.setStatus(value);
            taskRepository.save(task);

            return SUCCESS_RESPONSE("Status successfully changed!");
        }catch (Exception e) {
            logger.error("Error while changing status of!");
            return ERROR_RESPONSE(
                    error_while_operation("task", "changing status of"),
                    500
            );
        }
    }

    @Override
    public ResponseEntity<?> appointPerformer(Integer taskId, Integer performerId) {
        try {
            Optional<Task> optional = taskRepository.findById(taskId);
            if(optional.isEmpty())
                return ERROR_RESPONSE(
                        incorrect_id("task", taskId),
                        404
                );
            if(!userRepository.existsById(performerId))
                return ERROR_RESPONSE(
                        incorrect_id("user", taskId),
                        404
                );
            Task task = optional.get();
            task.setPerformerId(performerId);
            taskRepository.save(task);

            return SUCCESS_RESPONSE("Performer successfully appointed!");
        }catch (Exception e) {
            logger.error("Error while appointing performer!");
            return ERROR_RESPONSE(
                    error_while_operation("performer of task", "appointing"),
                    500
            );
        }
    }

    @Override
    public ResponseEntity<?> addComment(CommentDto commentDto) {
        try {
            Comment comment = Comment.builder()
                    .text(commentDto.getText())
                    .task(Task.builder().id(commentDto.getTask().getId()).build())
                    .build();
            commentRepository.save(comment);
            return SUCCESS_RESPONSE("Comment successfully added!");
        }catch (Exception e) {
            logger.error("Error while comment performer!");
            return ERROR_RESPONSE(
                    error_while_operation("comment", "is being added"),
                    500
            );
        }
    }




}







