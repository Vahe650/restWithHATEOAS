package com.taskemployeerest.rest.hateoas;


import com.taskemployeerest.rest.controller.EmployeeEndpoint;
import com.taskemployeerest.rest.controller.TaskEndpoint;
import com.taskemployeerest.rest.model.Employer;
import com.taskemployeerest.rest.model.Task;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class EmployeeResource extends ResourceSupport {

  private final Employer employer;

  public EmployeeResource(final Employer employer) {
    this.employer = employer;
    final int id = employer.getId();
    add(linkTo(methodOn(EmployeeEndpoint.class).allElmployees()).withRel("all"));
    add(linkTo(methodOn(TaskEndpoint.class).getAllByEmployee(id)).withRel("getEmployeeSTasks"));
    add(linkTo(methodOn(EmployeeEndpoint.class).get(id)).withRel("employee"));
    add(linkTo(methodOn(EmployeeEndpoint.class).delete(id)).withRel("deleteEmployee"));
  }
}