package me.ricky.guides.testingguides.service;

import lombok.RequiredArgsConstructor;
import me.ricky.guides.testingguides.exception.ResourceNotFoundException;
import me.ricky.guides.testingguides.model.Employee;
import me.ricky.guides.testingguides.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> existsEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (existsEmployee.isPresent()) {
            throw new ResourceNotFoundException("Employee with email " + employee.getEmail() + " already exists");
        }

        return employeeRepository.save(employee);
    }
}