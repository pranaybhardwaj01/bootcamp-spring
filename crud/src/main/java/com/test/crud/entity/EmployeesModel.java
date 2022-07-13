package com.test.crud.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class EmployeesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empId;
    private String empName;
    private String empDepartment;
    private String empContact;

}
