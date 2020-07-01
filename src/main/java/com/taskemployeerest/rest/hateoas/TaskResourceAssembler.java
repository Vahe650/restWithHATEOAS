package com.taskemployeerest.rest.hateoas;

import com.taskemployeerest.rest.controller.TaskEndpoint;
import com.taskemployeerest.rest.model.Task;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class TaskResourceAssembler extends
        ResourceAssemblerSupport<Task, TaskResource> {
    public TaskResourceAssembler() {
        super(TaskEndpoint.class, TaskResource.class);
    }

    @Override
    public TaskResource toResource(Task task) {
        return createResourceWithId(task.getId(), task);
    }

    @Override
    protected TaskResource instantiateResource(Task  task) {
        return new TaskResource(task);
    }
}
