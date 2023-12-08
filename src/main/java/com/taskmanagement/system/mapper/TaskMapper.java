package com.taskmanagement.system.mapper;

import com.taskmanagement.system.dto.CommentDto;
import com.taskmanagement.system.dto.TaskDto;
import com.taskmanagement.system.entity.Comment;
import com.taskmanagement.system.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "comments", qualifiedByName = "toDto")
    TaskDto toDto(Task task);
    Task toEntity(TaskDto taskDto);

    @Named("toDto")
    default List<CommentDto> toDto(List<Comment> comments) {
        return comments != null ?
                comments.stream().map(c ->
                        CommentDto.builder()
                                .id(c.getId())
                                .text(c.getText())
                                .build()
                ).toList() :
                null;
    }

}
