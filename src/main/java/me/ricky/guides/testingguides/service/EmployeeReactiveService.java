package me.ricky.guides.testingguides.service;

import me.ricky.guides.testingguides.dto.EmployeeDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeReactiveService {
    Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto);
    Mono<EmployeeDto> getEmployeeById(String id);
    Flux<EmployeeDto> getAllEmployees();
    Mono<EmployeeDto> updateEmployee(String id, EmployeeDto employeeDto);
}
