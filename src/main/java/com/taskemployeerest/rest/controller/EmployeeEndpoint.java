package com.taskemployeerest.rest.controller;

import com.taskemployeerest.rest.hateoas.EmployeeResource;
import com.taskemployeerest.rest.model.Employer;
import com.taskemployeerest.rest.repository.EmployerRepository;
import com.taskemployeerest.rest.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class EmployeeEndpoint {
    private EmployerRepository employerRepository;
    private TaskRepository taskRepository;


    @GetMapping("/all")
    public ResponseEntity<Resources<EmployeeResource>> allElmployees(){
        List<EmployeeResource> all = employerRepository.findAll().stream().map(EmployeeResource::new).collect(Collectors.toList());;
        final Resources<EmployeeResource> resources = new Resources<>(all);
        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return ResponseEntity.ok(resources);

    }

    @GetMapping("/getOneEmployee/{id}")
    public ResponseEntity get(@PathVariable(name = "id")int id){
        Optional<Employer> one = employerRepository.findById(id);
        return one.<ResponseEntity>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body("empolyee with " + id + " id is not present"));
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity delete(@PathVariable(name = "id")int id){
        Optional<Employer> one = employerRepository.findById(id);
        if (one.isPresent()) {
            employerRepository.delete(one.get());
            return ResponseEntity.ok(one.get().getName()+" is deleted");
        }
        return ResponseEntity.badRequest().body("empolyee with " + id + " id is not present");

    }
    @PostMapping("/saveEmployee")
    public void saveEmployee(@RequestBody Employer employer){
        employerRepository.save(employer);
    }


    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity updateEmployee(@RequestBody Employer employer,@PathVariable("id")int id){
        Optional<Employer> one=employerRepository.findById(id);
        if (!one.isPresent()) {
            return ResponseEntity.badRequest().body("empolyee with " + id + " id is not present");
        }
        employer.setId(id);
        employerRepository.save(employer);
        return ResponseEntity.ok(employer);
    }
}
