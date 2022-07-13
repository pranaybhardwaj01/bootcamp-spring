package com.test.crud.service;

import java.util.List;

import com.test.crud.entity.EmployeesModel;

public interface EmployeesService {

   EmployeesModel saveEmployee(EmployeesModel employee);

   List<EmployeesModel> fetchEmployeesList();

   EmployeesModel updateEmployeeDetails(EmployeesModel employee, Long empId);

   void deleteEmployeeById(Long empId);
}
