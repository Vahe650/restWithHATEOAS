package com.taskemployeerest.rest.controller;

import com.taskemployeerest.hateoas.EmployeeResource;
import com.taskemployeerest.rest.model.Employer;
import com.taskemployeerest.rest.model.Task;
import com.taskemployeerest.rest.repository.EmployerRepository;
import com.taskemployeerest.rest.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class TaskEndpoint {
    private EmployerRepository employerRepository;
    private TaskRepository taskRepository;


    @GetMapping("/allTasks")
    public ResponseEntity allTaska() {
        List<Task> all = taskRepository.findAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping("addTask/{employeeId}")
    public void addTask(@RequestBody Task task, @PathVariable("employeeId") int id) {
        Optional<Employer> employerById = employerRepository.findById(id);
        task.setEmployer(employerById.get());
        taskRepository.save(task);
    }

    @GetMapping("/getOneTask/{id}")
    public ResponseEntity one(@PathVariable(name = "id") int id) {
        Optional<Task> one = taskRepository.findById(id);
        return one.<ResponseEntity>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body("taask with " + id + " id is not present"));
    }

    @GetMapping("/getAllByEmployee/{employeeId}")
    public ResponseEntity getAllByEmployee(@PathVariable(name = "employeeId") int employeeId) {
        Employer one = employerRepository.getOne(employeeId);
        List<Task> tasks = taskRepository.findByEmployerId(employeeId);
        Link link = ControllerLinkBuilder.linkTo(EmployeeResource.class)
                .slash("getOneEmployee").slash(one.getId()).withSelfRel();
        return ResponseEntity.ok(tasks);
    }


    @DeleteMapping("/deleteTask{id}")
    public ResponseEntity delete(@PathVariable(name = "id") int id) {
        Optional<Task> one = taskRepository.findById(id);
        if (one.isPresent()) {
            taskRepository.delete(one.get());
            return ResponseEntity.ok(one.get().getTitle() + " is deleted");
        }
        return ResponseEntity.badRequest().body("empolyee with " + id + " id is not present");
    }


    @PutMapping("/update/{taskID}")
    public ResponseEntity update(@PathVariable("taskID") int id, @RequestBody Task task) {
        Optional<Task> byId = taskRepository.findById(id);
        if (!byId.isPresent()) {
            return ResponseEntity.badRequest().body("task with " + id + " id is not present");
        }
        task.setId(id);
        taskRepository.save(task);
        return ResponseEntity.ok(task);

    }
}