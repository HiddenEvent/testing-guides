package me.ricky.guides.testingguides.repository;

import me.ricky.guides.testingguides.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}