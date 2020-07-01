//package com.taskemployeerest.rest.messaging;
//
//import com.taskemployeerest.rest.model.Task;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class JmsOrderMessagingService implements TaskMessagingService {
//    private JmsTemplate jms;
//
//    @Autowired
//    public JmsOrderMessagingService(JmsTemplate jms) {
//        this.jms = jms;
//    }
//
//    @Override
//    public void sendOrder(Task task) {
//        jms.convertAndSend("tacocloud.order.queue", task, message -> {
//            message.setStringProperty("X_ORDER_SOURCE", "WEB");
//            return message;
//        });
//    }
//
//
//}
