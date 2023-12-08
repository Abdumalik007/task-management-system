package com.taskmanagement.system.repository;

import com.taskmanagement.system.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    Page<Task> findByAuthorId(@Param("id") Integer id, Pageable pageable);
    Page<Task> findByPerformerId(@Param("id") Integer id, Pageable pageable);


}