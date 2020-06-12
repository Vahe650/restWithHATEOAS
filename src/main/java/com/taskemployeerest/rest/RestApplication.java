package com.taskemployeerest.rest;

import com.taskemployeerest.rest.model.Employer;
import com.taskemployeerest.rest.model.Task;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.jms.Destination;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Destination orderQueue() {
        return new ActiveMQQueue("taskEmployee.com");
    }
    @Bean
    public MappingJackson2MessageConverter messageConverter() {
        MappingJackson2MessageConverter messageConverter =
                new MappingJackson2MessageConverter();
        messageConverter.setTypeIdPropertyName("_typeId");
        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("task", Task.class);
        messageConverter.setTypeIdMappings(typeIdMappings);
        return messageConverter;
    }

    @Bean
    public ResourceProcessor<PagedResources<Resource<Employer>>>
    tacoProcessor(EntityLinks links) {
        return resource -> {
            resource.add(
                    links.linkFor(Employer.class)
                            .slash("all")
                            .withRel("recents"));
            return resource;
        };
    }
}
