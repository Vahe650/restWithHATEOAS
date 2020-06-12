package com.taskemployeerest.rest.repository;



import com.taskemployeerest.rest.model.Degree;
import com.taskemployeerest.rest.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployerRepository extends JpaRepository<Employer,Integer> {
    Optional<Employer> findByName(String name);
    List<Employer> findByDegree(Degree degree);

}
