package me.ricky.guides.testingguides.controller;

import lombok.RequiredArgsConstructor;
import me.ricky.guides.testingguides.dto.EmployeeDto;
import me.ricky.guides.testingguides.service.EmployeeReactiveService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
@RestController
@RequestMapping(EmployeeReactiveController.BASE_URL)
public class EmployeeReactiveController {
    public static final String BASE_URL = "/api/v1/reactive/employees";
    private final EmployeeReactiveService employeeReactiveService;
    @PostMapping
    public Mono<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeReactiveService.saveEmployee(employeeDto);
    }
    @GetMapping("{id}")
    public Mono<EmployeeDto> getEmployeeById(@PathVariable("id") String id) {
        return employeeReactiveService.getEmployeeById(id);
    }
    @GetMapping
    public Flux<EmployeeDto> getAllEmployees() {
        return employeeReactiveService.getAllEmployees();
    }
    @PutMapping("{id}")
    public Mono<EmployeeDto> updateEmployee(@PathVariable("id") String id, @RequestBody EmployeeDto employeeDto) {
        return employeeReactiveService.updateEmployee(id, employeeDto);
    }
}
