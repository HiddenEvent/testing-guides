package me.ricky.guides.testingguides.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.ricky.guides.testingguides.model.Employee;
import me.ricky.guides.testingguides.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(employee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(employee.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(employee.getEmail()));

    }

}