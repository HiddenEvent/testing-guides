package me.ricky.guides.testingguides.service;

import me.ricky.guides.testingguides.dto.EmployeeDto;
import reactor.core.publisher.Mono;

public interface EmployeeReactiveService {
    Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto);
    Mono<EmployeeDto> getEmployeeById(String id);
}
