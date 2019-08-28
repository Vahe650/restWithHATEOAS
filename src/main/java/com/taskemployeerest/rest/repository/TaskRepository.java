package com.taskemployeerest.rest.repository;


import com.taskemployeerest.rest.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer> {

    List<Task> findByEmployerId(int employeeId);

}
