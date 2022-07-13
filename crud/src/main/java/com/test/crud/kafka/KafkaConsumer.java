package com.test.crud.kafka;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.crud.entity.EmployeesModel;
import com.test.crud.redis.RedisUtils;
import java.util.concurrent.TimeUnit;

@Component
public class KafkaConsumer {
    @Autowired
    private RedisUtils<EmployeesModel> redisUtilsEmp;

    @KafkaListener(topics = "test_crud", groupId = "group1")
    public void listenGroup1(String message) {
        System.out.println("Received Message in group 1: " + message);
    }

    @KafkaListener(topics = "test_crud", groupId = "group2")
    public void listenGroup2(String message) {
        ObjectMapper mapper = new ObjectMapper();
        EmployeesModel emp;
        try {
            emp = mapper.readValue(message, EmployeesModel.class);
            cacheEmployeeDetail(emp);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("Received Message in group 2: " + message);
    }

    private void cacheEmployeeDetail(EmployeesModel emp) {
        redisUtilsEmp.putMap("Employees", "emp_" + emp.getEmpId(), emp);
        redisUtilsEmp.setExpire("emp_" + emp.getEmpId(), 1, TimeUnit.DAYS);
        System.out.print("Inserted in redis - " + emp.toString());
    }
}
