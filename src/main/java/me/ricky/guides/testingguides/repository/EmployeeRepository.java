package me.ricky.guides.testingguides.repository;

import me.ricky.guides.testingguides.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    @Query("SELECT e FROM Employee e WHERE e.firstName = ?1 AND e.lastName = ?2")
    Employee findByJPQL(String fistName, String lastName);

    Employee findByFirstNameAndLastName(String fistName, String lastName);
}