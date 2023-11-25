package me.ricky.guides.testingguides.service;

import lombok.RequiredArgsConstructor;
import me.ricky.guides.testingguides.exception.ResourceNotFoundException;
import me.ricky.guides.testingguides.model.Employee;
import me.ricky.guides.testingguides.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        employeeRepository.findByEmail(employee.getEmail())
                .ifPresent(e -> {
                    throw new ResourceNotFoundException("Email already exists");
                });
        return employeeRepository.save(employee);
    }
}
