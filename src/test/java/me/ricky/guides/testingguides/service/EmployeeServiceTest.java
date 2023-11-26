package me.ricky.guides.testingguides.service;

import me.ricky.guides.testingguides.exception.ResourceNotFoundException;
import me.ricky.guides.testingguides.model.Employee;
import me.ricky.guides.testingguides.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

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
    void service_저장() {
        //given
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        //when
        Employee savedEmployee = employeeService.saveEmployee(employee);

        //then
        assertThat(savedEmployee).isNotNull();
    }
    @Test
    void service_저장_email중복() {
        //given
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        //when
        assertThrows(ResourceNotFoundException.class, () -> employeeService.saveEmployee(employee));

        //then
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void service_전체조회() {
        //given
        Employee employee1 = Employee.builder()
                .id(1L)
                .firstName("Ricky1")
                .lastName("Kim1")
                .email("aa1@a.com")
                .build();
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        //when
        List<Employee> employees = employeeService.getAllEmployees();

        //then
        assertThat(employees).isNotNull();
        assertThat(employees).hasSize(2);
    }

    @Test
    void service_전체조회_empty() {
        //given
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        //when
        List<Employee> employees = employeeService.getAllEmployees();

        //then
        assertThat(employees).isEmpty();
        assertThat(employees).hasSize(0);
    }

    @Test
    void service_상세() {
        //given
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        //when
        Employee employeeDB = employeeService.getEmployeeById(1L);

        //then
        assertThat(employeeDB).isNotNull();

    }

}