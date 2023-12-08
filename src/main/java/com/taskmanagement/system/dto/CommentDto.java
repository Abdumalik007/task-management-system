package com.taskmanagement.system.dto;


import com.taskmanagement.system.entity.Comment;
import com.taskmanagement.system.entity.Task;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentDto {

    private Integer id;
    @NotBlank(message = "Text must not be empty")
    private String text;
    @NotNull
    private Task task;
}
