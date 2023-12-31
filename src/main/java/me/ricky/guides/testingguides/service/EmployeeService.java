package me.ricky.guides.testingguides.service;

import me.ricky.guides.testingguides.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);
    Employee updateEmployee(Employee employee);
    void deleteEmployee(Long id);
}
