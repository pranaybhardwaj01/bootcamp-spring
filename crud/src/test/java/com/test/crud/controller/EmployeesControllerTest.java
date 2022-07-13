package com.test.crud.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.crud.entity.EmployeesModel;
import com.test.crud.service.EmployeesService;

@WebMvcTest(EmployeesController.class)
public class EmployeesControllerTest {
    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    EmployeesController empController;

    @MockBean
    EmployeesService empService;

    static EmployeesModel emReq = new EmployeesModel();

    @BeforeAll
    public static void init() {
        emReq.setEmpContact("0000000000");
        emReq.setEmpDepartment("Engineering");
        emReq.setEmpName("Pranay");
    }

    @Test
    public void get_emp_detail_successfully() throws Exception {
        Mockito.when(empService.fetchEmployeesList()).thenReturn(new ArrayList<>());
        System.out.println("fetch");
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        assertEquals(HttpStatus.OK.value(), res.getResponse().getStatus());
        assertEquals(new ArrayList<>().toString(), res.getResponse().getContentAsString());
    }

    @Test
    public void save_emp_detail_successfully() throws Exception {

        EmployeesModel emRes = new EmployeesModel();
        emRes.setEmpContact("0000000000");
        emRes.setEmpDepartment("Engineering");
        emRes.setEmpId(1L);
        emRes.setEmpName("Pranay");

        Mockito.when(empService.saveEmployee(emReq)).thenReturn(emRes);
        System.out.println("Save");

        ObjectMapper map = new ObjectMapper();

        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/employee").contentType(MediaType.APPLICATION_JSON)
                .content(map.writeValueAsBytes(emReq))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        assertEquals(HttpStatus.OK.value(), res.getResponse().getStatus());
        assertEquals(map.writeValueAsString(emRes), res.getResponse().getContentAsString());
    }

    @Test
    public void update_emp_detail_successfully() throws Exception {

        EmployeesModel emRes = new EmployeesModel();
        emRes.setEmpContact("0000000000");
        emRes.setEmpDepartment("Engineering");
        emRes.setEmpId(1L);
        emRes.setEmpName("Shivam");

        Mockito.when(empService.updateEmployeeDetails(emReq, 1L)).thenReturn(emRes);
        System.out.println("Update");

        ObjectMapper map = new ObjectMapper();

        MvcResult res = mockMvc
                .perform(MockMvcRequestBuilders.put("/employee/1").contentType(MediaType.APPLICATION_JSON)
                        .content(map.writeValueAsBytes(emReq)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        assertEquals(HttpStatus.OK.value(), res.getResponse().getStatus());
        assertEquals(map.writeValueAsString(emRes), res.getResponse().getContentAsString());
    }

    @Test
    public void delete_emp_detail_successfully() throws Exception {
        ;

        System.out.println("Delete");

        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.delete("/employee/1"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        assertEquals(HttpStatus.OK.value(), res.getResponse().getStatus());
    }
}