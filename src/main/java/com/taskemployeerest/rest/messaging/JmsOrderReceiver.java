package com.taskemployeerest.rest.messaging;

import com.taskemployeerest.rest.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsOrderReceiver implements OrderReceiver {
    private JmsTemplate jms;

    @Autowired
    public JmsOrderReceiver(JmsTemplate jms) {
        this.jms = jms;
    }

    public Task taskResourceReciever() {
        return (Task) jms.receiveAndConvert("taskEmployee.com");
    }

    @JmsListener(destination = "taskEmployee.com")
    public void receiveOrder(Task task) {
        System.out.println(task);
    }

}