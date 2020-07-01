//package com.taskemployeerest.rest.controller;
//
//import com.taskemployeerest.rest.model.Task;
//import com.taskemployeerest.rest.repository.TaskRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.data.rest.webmvc.RepositoryRestController;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.jms.JMSException;
//import javax.jms.Message;
//import javax.persistence.EntityExistsException;
//
//@RepositoryRestController
//@AllArgsConstructor
//@RequestMapping(value = "api")
//public class SendMessageEndpoint {
//
//    private TaskRepository taskRepository;
//    private JmsTemplate jms;
//
//    @GetMapping("/convertAndSend/task")
//    public String convertAndSendOrder() {
//        Task task = taskRepository.findTop1ByOrderByIdDesc().orElseThrow(EntityExistsException::new);
//        jms.convertAndSend("taskEmploye.com", task,this::addOrderSource);
//        return "Convert and sent task";
//    }
//    private Message addOrderSource(Message message) throws JMSException {
//        message.setStringProperty("from task employer", "WEB");
//        return message;
//    }
//}
