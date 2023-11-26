package me.ricky.guides.testingguides.service;

import me.ricky.guides.testingguides.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployees();
}
