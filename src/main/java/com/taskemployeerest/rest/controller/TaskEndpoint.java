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
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
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
    public void addTask(@RequestBody Task task, @PathVariable("employeeId") int id) {
        Optional<Employer> employerById = employerRepository.findById(id);
        task.setEmployer(employerById.get());
        taskRepository.save(task);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public TaskResource one(@PathVariable(name = "id") int id) {
        Optional<Task> one = taskRepository.findById(id);
        return one.map(task -> new TaskResourceAssembler().toResource(task)).orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping("/employee/{employeeId}")
    public Resources<TaskResource>  getAllByEmployee(@PathVariable(name = "employeeId") int employeeId) {
        List<Task> tasks = taskRepository.findByEmployerId(employeeId);
        List<TaskResource> taskResources =  new TaskResourceAssembler().toResources(tasks);
        Resources<TaskResource> tasksByEmployee = new Resources<>(taskResources);
        tasksByEmployee.add(
                linkTo(methodOn(TaskEndpoint.class).getAllByEmployee(employeeId))
                        .withRel("employee"));
        return tasksByEmployee;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") int id) {
        Optional<Task> one = taskRepository.findById(id);
        if (one.isPresent()) {
            taskRepository.delete(one.get());
            return ResponseEntity.ok(one.get().getTitle() + " is deleted");
        }
        return ResponseEntity.badRequest().body("empolyee with " + id + " id is not present");
    }


    @PutMapping("/{taskID}")
    public ResponseEntity update(@PathVariable("taskID") int id, @RequestBody Task task) {
        Optional<Task> byId = taskRepository.findById(id);
        if (!byId.isPresent()) {
            return ResponseEntity.badRequest().body("task with " + id + " id is not present");
        }
        task.setId(id);
        taskRepository.save(task);
        return ResponseEntity.ok(task);

    }

    @ResponseBody
    @GetMapping("/fromRest/{id}")
    public Task getIngredientById(@PathVariable("id") String ingredientId) {
        return restTemplate.getForObject("http://localhost:8090/api/tasks/{id}",Task.class, ingredientId);
    }
}