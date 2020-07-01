package com.taskemployeerest.rest.controller;

import com.taskemployeerest.rest.hateoas.EmployeeResource;
import com.taskemployeerest.rest.hateoas.TaskResource;
import com.taskemployeerest.rest.hateoas.TaskResourceAssembler;
import com.taskemployeerest.rest.model.Employer;
import com.taskemployeerest.rest.model.Task;
import com.taskemployeerest.rest.repository.EmployerRepository;
import com.taskemployeerest.rest.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController
@AllArgsConstructor
@RequestMapping(value = "api/tasks")
public class TaskEndpoint {
    private EmployerRepository employerRepository;
    private TaskRepository taskRepository;
    private RestTemplate restTemplate;


    @GetMapping("/all")
    public ResponseEntity<Resources<TaskResource>> alltasks() {
        List<TaskResource> taskResources =
                new TaskResourceAssembler().toResources(taskRepository.findAll());
        Resources<TaskResource> recentResources = new Resources<>(taskResources);
        recentResources.add(
                linkTo(methodOn(TaskEndpoint.class).alltasks())
                        .withRel("all"));
        return new ResponseEntity<>(recentResources, HttpStatus.OK);
    }

    @PostMapping("/{employeeId}")
    public ResponseEntity<TaskResource> save(@RequestBody Task task, @PathVariable("employeeId") int id) {
        Optional<Employer> employerById = employerRepository.findById(id);
        return employerById.map(employer -> {
            task.setEmployer(employer);
            taskRepository.save(task);
            final URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(task.getId()).toUri();
            return ResponseEntity.created(uri).body(new TaskResource(task));
        }).orElseThrow(EntityNotFoundException::new);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public TaskResource one(@PathVariable(name = "id") int id) {
        Optional<Task> one = taskRepository.findById(id);
        return one.map(task -> new TaskResourceAssembler().toResource(task)).orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping("/employee/{employeeId}")
    public Resources<TaskResource> getAllTasksByEmployee(@PathVariable(name = "employeeId") int employeeId) {
        List<Task> tasks = taskRepository.findByEmployerId(employeeId);
        List<TaskResource> taskResources = new TaskResourceAssembler().toResources(tasks);
        Resources<TaskResource> tasksByEmployee = new Resources<>(taskResources);
        tasksByEmployee.add(linkTo(methodOn(TaskEndpoint.class).getAllTasksByEmployee(employeeId))
                        .withRel("employee"));
        return tasksByEmployee;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        try {
            return taskRepository.findById(id).map(task -> {
                taskRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            }).orElseThrow(EntityNotFoundException::new);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    @PutMapping("/{id}")
    public ResponseEntity<TaskResource> update(@PathVariable("id") int id, @RequestBody Task task) {
        Optional<Task> byId = taskRepository.findById(id);
        return byId.map((taskFromDb) -> {
            task.setId(id);
            taskRepository.save(task);
            final TaskResource resource = new TaskResource(task);
            final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
            return ResponseEntity.created(uri).body(resource);
        }).orElseThrow(EntityNotFoundException::new);


    }

    @ResponseBody
    @GetMapping("/fromRest/{id}")
    public Task getIngredientById(@PathVariable("id") String ingredientId) {
        return restTemplate.getForObject("http://localhost:8090/api/tasks/{id}", Task.class, ingredientId);
    }
}
