package com.taskemployeerest.rest.messaging;

import com.taskemployeerest.rest.hateoas.TaskResource;
import com.taskemployeerest.rest.model.Task;

public interface TaskMessagingService {

    void sendOrder(Task task);
}
