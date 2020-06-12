package com.taskemployeerest.rest.controller;

import com.taskemployeerest.rest.hateoas.EmployeeResource;
import com.taskemployeerest.rest.hateoas.EmployerResourceAssembler;
import com.taskemployeerest.rest.model.Employer;
import com.taskemployeerest.rest.repository.EmployerRepository;
import com.taskemployeerest.rest.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/employers")
public class EmployeeEndpoint {
    private EmployerRepository employerRepository;


    @GetMapping("/all")
    public ResponseEntity<Resources<EmployeeResource>>allElmployees() {
        List<EmployeeResource> tacoResources =
                new EmployerResourceAssembler().toResources(employerRepository.findAll());
        Resources<EmployeeResource> recentResources =
                new Resources<>(tacoResources);
        recentResources.add(
                linkTo(methodOn(EmployeeEndpoint.class).allElmployees())
                        .withRel("all"));
        return new ResponseEntity<>(recentResources, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public EmployeeResource get(@PathVariable(name = "id") int id) {
        Optional<Employer> one = employerRepository.findById(id);
        return one.map(employer -> new EmployerResourceAssembler().toResource(employer)).orElseThrow(EntityNotFoundException::new);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        try {
            return employerRepository.findById(id).map(employer -> {
                employerRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            }).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @PostMapping("/")
    public ResponseEntity<EmployeeResource> save(@RequestBody Employer employer) {
        employerRepository.save(employer);
        final URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(employer.getId()).toUri();
        return ResponseEntity.created(uri).body(new EmployeeResource(employer));


    }


    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResource> updateEmployee(@RequestBody Employer employer, @PathVariable("id") int id) {
        employer.setId(id);
        employerRepository.save(employer);
        final EmployeeResource resource = new EmployeeResource(employer);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).body(resource);
    }
}
