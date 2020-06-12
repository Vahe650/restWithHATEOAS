package com.taskemployeerest.rest.hateoas;

import com.taskemployeerest.rest.controller.EmployeeEndpoint;
import com.taskemployeerest.rest.model.Employer;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class EmployerResourceAssembler extends ResourceAssemblerSupport<Employer, EmployeeResource> {
    public EmployerResourceAssembler() {
        super(EmployeeEndpoint.class, EmployeeResource.class);
    }

    @Override
    protected EmployeeResource  instantiateResource(Employer employer) {
        return new EmployeeResource(employer);
    }

    @Override
    public EmployeeResource toResource(Employer employer) {
        return createResourceWithId(employer.getId(), employer);
    }
}