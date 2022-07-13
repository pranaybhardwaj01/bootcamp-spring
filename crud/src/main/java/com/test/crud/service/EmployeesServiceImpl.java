package com.test.crud.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.test.crud.entity.EmployeesModel;
import com.test.crud.kafka.KafkaProducer;
import com.test.crud.redis.RedisUtils;
import com.test.crud.repository.EmployeesRepository;

@Service

public class EmployeesServiceImpl implements EmployeesService {

    @Autowired
    private EmployeesRepository employees;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private RedisUtils<EmployeesModel> redisUtilsEmp;

    @Override
    public EmployeesModel saveEmployee(EmployeesModel emp) {
        EmployeesModel em = employees.save(emp);
        String kafkaMessage = generateMessageToBePushed(em).replace("\n", "");
        kafkaProducer.pushMessage(kafkaMessage);
        return em;
    }

    @Override
    public List<EmployeesModel> fetchEmployeesList() {
        Map<Object, EmployeesModel> list = redisUtilsEmp.getMapAsAll("Employees");
        List<EmployeesModel> result = new ArrayList<EmployeesModel>(list.values());
        return result;
    }

    @Override
    public EmployeesModel updateEmployeeDetails(EmployeesModel emp, Long empId) {
        EmployeesModel empDetails = employees.findById(empId).get();
        if (Objects.nonNull(emp.getEmpName())
                && !"".equalsIgnoreCase(
                        emp.getEmpName())) {
            empDetails.setEmpName(
                    emp.getEmpName());
        }

        if (Objects.nonNull(emp.getEmpContact())) {
            empDetails.setEmpContact(
                    emp.getEmpContact());
        }

        if (Objects.nonNull(emp.getEmpDepartment())) {
            empDetails.setEmpDepartment(
                    emp.getEmpDepartment());
        }

        return employees.save(empDetails);
    }

    @Override
    public void deleteEmployeeById(Long empId) {
        employees.deleteById(empId);
    }

    private String generateMessageToBePushed(EmployeesModel em) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(em);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

}
