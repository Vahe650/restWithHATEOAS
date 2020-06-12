package com.taskemployeerest.rest.repository;


import com.taskemployeerest.rest.model.Task;
import com.taskemployeerest.rest.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Integer> {

    List<Task> findByEmployerId(int employeeId);
    List<Task> findByTitleContaining(String title);
    Optional<Task> findByDescription(String description);
    Optional<Task> findTop1ByOrderByIdDesc();
    List<Task> findByStatus(TaskStatus status);

}
