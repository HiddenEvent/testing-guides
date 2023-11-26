package me.ricky.guides.testingguides.service;

import me.ricky.guides.testingguides.model.Employee;
import me.ricky.guides.testingguides.repository.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {
    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @BeforeEach
    void setup() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    @Test
    void service_저장() {
        //given
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Ricky")
                .lastName("Kim")
                .email("aa@a.com")
                .build();
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        //when
        Employee savedEmployee = employeeService.saveEmployee(employee);

        //then
        Assertions.assertThat(savedEmployee).isNotNull();

    }

}