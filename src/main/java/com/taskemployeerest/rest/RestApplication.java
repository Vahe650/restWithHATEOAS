package com.taskemployeerest.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public Destination orderQueue() {
//        return new ActiveMQQueue("taskEmployee.com");
//    }
//    @Bean
//    public MappingJackson2MessageConverter messageConverter() {
//        MappingJackson2MessageConverter messageConverter =
//                new MappingJackson2MessageConverter();
//        messageConverter.setTypeIdPropertyName("_typeId");
//        Map<String, Class<?>> typeIdMappings = new HashMap<>();
//        typeIdMappings.put("task", Task.class);
//        messageConverter.setTypeIdMappings(typeIdMappings);
//        return messageConverter;
//    }
//
//    @Bean
//    public ResourceProcessor<PagedResources<Resource<Employer>>>
//    tacoProcessor(EntityLinks links) {
//        return resource -> {
//            resource.add(
//                    links.linkFor(Employer.class)
//                            .slash("all")
//                            .withRel("recents"));
//            return resource;
//        };
//    }


}
