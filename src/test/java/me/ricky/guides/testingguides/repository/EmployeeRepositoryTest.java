package me.ricky.guides.testingguides.repository;

import me.ricky.guides.testingguides.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Test
    void 저장로직(){
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

}