package com.taskemployeerest.rest.messaging;

import com.taskemployeerest.rest.hateoas.TaskResource;
import com.taskemployeerest.rest.hateoas.TaskResourceAssembler;
import com.taskemployeerest.rest.model.Task;

public interface OrderReceiver {

    Task taskResourceReciever();
}
