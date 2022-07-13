package com.test.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.test.crud.entity.EmployeesModel;
import com.test.crud.service.EmployeesService;
import org.springframework.web.bind.annotation.*;

@RestController

public class EmployeesController {
    @Autowired
    private EmployeesService empService;

    @PostMapping("/employee")
    public EmployeesModel saveEmployee(@RequestBody EmployeesModel emp) {
        return empService.saveEmployee(emp);

    }

    @GetMapping("/employees")
    public List<EmployeesModel> getAllEmployess() {
        return empService.fetchEmployeesList();
    }

    @PutMapping("/employee/{id}")
    public EmployeesModel updateEmployee(@RequestBody EmployeesModel emp, @PathVariable("id") Long empId) {
        return empService.updateEmployeeDetails(emp, empId);
    }

    @DeleteMapping("/employee/{id}")
    public void deleteEmployee(@PathVariable("id") Long empId) {
        empService.deleteEmployeeById(empId);
    }
}
