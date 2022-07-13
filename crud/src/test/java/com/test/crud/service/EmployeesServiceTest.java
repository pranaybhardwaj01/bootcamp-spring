package com.test.crud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.test.crud.entity.EmployeesModel;
import com.test.crud.kafka.KafkaProducer;
import com.test.crud.redis.RedisUtils;
import com.test.crud.repository.EmployeesRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeesServiceTest {

    @InjectMocks
    EmployeesServiceImpl empService;

    @Mock
    EmployeesRepository employeesRepository;

    @Mock
    KafkaProducer kafkaProducer;

    @Mock
    RedisUtils<EmployeesModel> redisUtils;

    static EmployeesModel emReq = new EmployeesModel();

    @BeforeAll
    public static void init() {
        emReq.setEmpContact("0000000000");
        emReq.setEmpDepartment("Engineering");
        emReq.setEmpName("Pranay");
    }

    @Test
    public void get_emp_detail_successfully() throws Exception {
        Mockito.when(redisUtils.getMapAsAll("Employees")).thenReturn(new HashMap<Object, EmployeesModel>());
        System.out.println("fetch all");
        List<EmployeesModel> res = empService.fetchEmployeesList();
        assertEquals(new ArrayList<>().toString(), res.toString());
    }

    @Test
    public void save_emp_detail_successfully() throws Exception {
        EmployeesModel emRes = new EmployeesModel();
        emRes.setEmpContact("0000000000");
        emRes.setEmpDepartment("Engineering");
        emRes.setEmpId(1L);
        emRes.setEmpName("Pranay");
        Mockito.when(employeesRepository.save(emReq)).thenReturn(emRes);
        System.out.println("save");
        EmployeesModel res = empService.saveEmployee(emReq);
        assertEquals(emRes.toString(), res.toString());
    }

    @Test
    public void update_emp_detail_successfully() throws Exception {
        EmployeesModel emRes = new EmployeesModel();
        emRes.setEmpContact("0000000000");
        emRes.setEmpDepartment("Engineering");
        emRes.setEmpId(1L);
        emRes.setEmpName("Shivam");

        emReq.setEmpName("Shivam");
        Mockito.when(employeesRepository.save(emReq)).thenReturn(emRes);
        System.out.println("update");
        EmployeesModel res = empService.saveEmployee(emReq);
        assertEquals(emRes.toString(), res.toString());
    }
}
