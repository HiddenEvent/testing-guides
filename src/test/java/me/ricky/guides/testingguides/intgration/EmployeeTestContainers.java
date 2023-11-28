package me.ricky.guides.testingguides.intgration;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.ricky.guides.testingguides.controller.EmployeeController;
import me.ricky.guides.testingguides.model.Employee;
import me.ricky.guides.testingguides.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EmployeeTestContainers extends AbstractionBaseTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private Employee employee;

    @BeforeEach
    void setup() {
        employeeRepository.deleteAll();
        employee = Employee.builder()
                .firstName("Ricky")
                .lastName("Kim")
                .email("aa@a.com")
                .build();
    }

    @Test
    void it_생성() throws Exception {
        //given

        //when
        ResultActions response = mockMvc.perform(post(EmployeeController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        //then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(employee.getLastName()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()));

    }
    @Test
    void it_전체조회() throws Exception {
        //given
        Employee employee1 = Employee.builder()
                .firstName("Ricky1")
                .lastName("Kim1")
                .email("aa1@a.com").build();
        List<Employee> employees = List.of(employee, employee1);
        employeeRepository.saveAll(employees);

        //when
        ResultActions response = mockMvc.perform(get("/api/employees"));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(employees.size()));

    }

    @Test
    void it_상세() throws Exception {
        //given
        employee = employeeRepository.save(employee);

        //when
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(employee.getLastName()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()));

    }
    @Test
    void it_수정() throws Exception {
        //given
        Employee savedEmployee = employeeRepository.save(employee);

        Employee updatedEmployee = Employee.builder()
                .firstName("updF")
                .lastName("updL")
                .email("upd@a.com")
                .build();


        //when
        ResultActions perform = mockMvc.perform(put("/api/employees/{id}", savedEmployee.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(updatedEmployee.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(updatedEmployee.getLastName()))
                .andExpect(jsonPath("$.email").value(updatedEmployee.getEmail()));

    }
    @Test
    void it_삭제() throws Exception {
        //given
        Employee savedEmployee = employeeRepository.save(employee);

        //when
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", savedEmployee.getId()));

        //then
        response.andDo(print())
                .andExpect(status().isNoContent());
    }

}
