package com.test.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.crud.entity.EmployeesModel;

@Repository
public interface EmployeesRepository extends JpaRepository<EmployeesModel, Long> {

}
