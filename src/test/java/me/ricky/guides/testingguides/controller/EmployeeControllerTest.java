package me.ricky.guides.testingguides.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.ricky.guides.testingguides.model.Employee;
import me.ricky.guides.testingguides.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper objectMapper;
    private Employee employee;

    @BeforeEach
    void setup() {
        employee = Employee.builder()
                .id(1L)
                .firstName("Ricky")
                .lastName("Kim")
                .email("aa@a.com")
                .build();
    }

    @Test
    void controller_생성() throws Exception {
        //given
        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)));

        //then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(employee.getLastName()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()));

    }

    @Test
    void controller_전체조회() throws Exception {
        //given
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Ricky1")
                .lastName("Kim1")
                .email("aa1@a.com").build();
        List<Employee> employees = List.of(employee, employee1);
        given(employeeService.getAllEmployees()).willReturn(employees);

        //when
        ResultActions response = mockMvc.perform(get("/api/employees"));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(employees.size()));

    }

    @Test
    void controller_상세() throws Exception {
        //given
        given(employeeService.getEmployeeById(any()))
                .willReturn(employee);

        //when
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(employee.getLastName()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()));

    }

    @Test
    void controller_수정() throws Exception {
        //given
        Employee updatedEmployee = Employee.builder()
                .firstName("updF")
                .lastName("updL")
                .email("upd@a.com")
                .build();
        given(employeeService.updateEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        //when
        ResultActions perform = mockMvc.perform(put("/api/employees/{id}", employee.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(updatedEmployee.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(updatedEmployee.getLastName()))
                .andExpect(jsonPath("$.email").value(updatedEmployee.getEmail()));

    }
}