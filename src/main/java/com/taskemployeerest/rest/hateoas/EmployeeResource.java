package com.taskemployeerest.rest.hateoas;


import com.taskemployeerest.rest.model.Degree;
import com.taskemployeerest.rest.model.Employer;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.List;

@Getter
@Relation(value="employee", collectionRelation="employees")
public class EmployeeResource extends ResourceSupport {

    private static final TaskResourceAssembler taskResourceAsambler = new TaskResourceAssembler();

    private String name;

    private String surname;

    private Degree degree;

    private List<TaskResource> tasks;

    public EmployeeResource(Employer employer) {
        this.name = employer.getName();
        this.surname = employer.getSurname();
        this.degree = employer.getDegree();
        this.tasks = taskResourceAsambler.toResources(employer.getTasks());
    }
}