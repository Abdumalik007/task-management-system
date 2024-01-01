package com.taskmanagement.system.mapper;

import com.taskmanagement.system.dto.CommentDto;
import com.taskmanagement.system.dto.TaskDto;
import com.taskmanagement.system.entity.Comment;
import com.taskmanagement.system.entity.Task;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-01T21:49:10+0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDto toDto(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskDto.TaskDtoBuilder taskDto = TaskDto.builder();

        taskDto.comments( toDto( task.getComments() ) );
        taskDto.id( task.getId() );
        taskDto.title( task.getTitle() );
        taskDto.description( task.getDescription() );
        taskDto.priority( task.getPriority() );
        taskDto.status( task.getStatus() );
        taskDto.authorId( task.getAuthorId() );
        taskDto.performerId( task.getPerformerId() );

        return taskDto.build();
    }

    @Override
    public Task toEntity(TaskDto taskDto) {
        if ( taskDto == null ) {
            return null;
        }

        Task.TaskBuilder task = Task.builder();

        task.id( taskDto.getId() );
        task.title( taskDto.getTitle() );
        task.description( taskDto.getDescription() );
        task.status( taskDto.getStatus() );
        task.priority( taskDto.getPriority() );
        task.authorId( taskDto.getAuthorId() );
        task.performerId( taskDto.getPerformerId() );
        task.comments( commentDtoListToCommentList( taskDto.getComments() ) );

        return task.build();
    }

    protected Comment commentDtoToComment(CommentDto commentDto) {
        if ( commentDto == null ) {
            return null;
        }

        Comment.CommentBuilder comment = Comment.builder();

        comment.id( commentDto.getId() );
        comment.text( commentDto.getText() );
        comment.task( commentDto.getTask() );

        return comment.build();
    }

    protected List<Comment> commentDtoListToCommentList(List<CommentDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Comment> list1 = new ArrayList<Comment>( list.size() );
        for ( CommentDto commentDto : list ) {
            list1.add( commentDtoToComment( commentDto ) );
        }

        return list1;
    }
}
