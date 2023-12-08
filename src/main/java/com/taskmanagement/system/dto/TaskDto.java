package com.taskmanagement.system.dto;

import com.taskmanagement.system.entity.types.Priority;
import com.taskmanagement.system.entity.types.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TaskDto {
    private Integer id;
    @NotBlank(message = "Title must not be empty")
    private String title;
    @NotBlank(message = "Description must not be empty")
    private String description;
    @NotNull
    private Priority priority;
    @NotNull
    private Status status;
    @NotNull
    private Integer authorId;
    private Integer performerId;
    private List<CommentDto> comments;
}
