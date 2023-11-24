package me.ricky.guides.testingguides.repository;

import me.ricky.guides.testingguides.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Test
    void 저장(){
        //given
        Employee employee = Employee.builder()
                .firstName("Ricky")
                .lastName("Kim")
                .email("aa@a.com")
                .build();

        //when
        Employee savedEmployee = employeeRepository.save(employee);

        //then
        assertNotNull(savedEmployee);
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }
    @Test
    void 전체_조회() {
        //given
        Employee employee = Employee.builder()
                .firstName("Ricky")
                .lastName("Kim")
                .email("aa@a.com")
                .build();

        Employee employee1 = Employee.builder()
                .firstName("Ricky1")
                .lastName("Kim1")
                .email("aa1@a.com")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        //when
        List<Employee> employees = employeeRepository.findAll();

        //then
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(2);
    }
}