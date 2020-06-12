package com.taskemployeerest.rest.hateoas;

import com.taskemployeerest.rest.model.Employer;
import com.taskemployeerest.rest.model.Task;
import com.taskemployeerest.rest.model.TaskStatus;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

@Getter
public class TaskResource  extends ResourceSupport {

    private String title;

    private String description;

    private String assignedTime;

    private String endTime;

    private TaskStatus status;

    public TaskResource(Task task) {
        this.title=task.getTitle();
        this.description=task.getDescription();
        this.assignedTime=task.getAssignedTime();
        this.endTime=task.getEndTime();
        this.status=task.getStatus();
    }
}
