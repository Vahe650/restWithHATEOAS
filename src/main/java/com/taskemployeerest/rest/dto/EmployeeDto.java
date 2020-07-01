package com.taskemployeerest.rest.dto;

import com.taskemployeerest.rest.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDto {

    String name;

    List <Task> tasks;

}
