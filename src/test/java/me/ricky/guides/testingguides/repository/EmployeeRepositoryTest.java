package me.ricky.guides.testingguides.repository;

import me.ricky.guides.testingguides.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@DataJpaTest
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = Employee.builder()
                .firstName("Ricky")
                .lastName("Kim")
                .email("aa@a.com")
                .build();

    }

    @Test
    void 저장(){
        //given

        //when
        Employee savedEmployee = employeeRepository.save(employee);

        //then
        assertNotNull(savedEmployee);
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }
    @Test
    void 전체_조회() {
        //given
        Employee employee1 = Employee.builder()
                .firstName("Ricky")
                .lastName("Kim")
                .email("a@a.com")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        //when
        List<Employee> employees = employeeRepository.findAll();

        //then
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(2);
    }
    @Test
    void 상세() {
        //given

        employeeRepository.save(employee);

        //when
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        //then
        assertThat(employeeDB).isNotNull();
    }

    @Test
    void 이메일_검색() {
        //given
        employeeRepository.save(employee);

        //when
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        //then
        assertThat(employeeDB).isNotNull();
        assertThat(employeeDB.getEmail()).isEqualTo(employee.getEmail());
    }
    @Test
    void 수정() {
        //given
        employeeRepository.save(employee);
    
        //when
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setFirstName("Ricky1");
        savedEmployee.setLastName("Kim1");
        Employee updatedEmployee = employeeRepository.saveAndFlush(savedEmployee);


        //then
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Ricky1");
        assertThat(updatedEmployee.getLastName()).isEqualTo("Kim1");

    }

    @Test
    void 삭제() {
        //given
        employeeRepository.save(employee);

        //when
        employeeRepository.delete(employee);
        Optional<Employee> deletedEmployeeOptional = employeeRepository.findById(employee.getId());

        //then
        assertThat(deletedEmployeeOptional).isEmpty();

    }
    @Test
    void jpql_테스트() {
        //given
        employeeRepository.save(employee);

        //when
        Employee savedEmployee = employeeRepository.findByJPQL("Ricky", "Kim");

        //then
        assertThat(savedEmployee).isNotNull();
    }

}