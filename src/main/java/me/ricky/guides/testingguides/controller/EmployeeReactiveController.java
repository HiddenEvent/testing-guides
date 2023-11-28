package me.ricky.guides.testingguides.controller;

import lombok.RequiredArgsConstructor;
import me.ricky.guides.testingguides.dto.EmployeeDto;
import me.ricky.guides.testingguides.service.EmployeeReactiveService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
