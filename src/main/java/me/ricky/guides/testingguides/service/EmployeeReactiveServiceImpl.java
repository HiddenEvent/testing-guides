package me.ricky.guides.testingguides.service;

import lombok.RequiredArgsConstructor;
import me.ricky.guides.testingguides.dto.EmployeeDto;
import me.ricky.guides.testingguides.mapper.EmployeeMapper;
import me.ricky.guides.testingguides.model.EmployeeDoc;
import me.ricky.guides.testingguides.repository.EmployeeReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EmployeeReactiveServiceImpl implements EmployeeReactiveService {
    private final EmployeeReactiveRepository employeeReactiveRepository;

    @Override
    public Mono<EmployeeDto> saveEmployee(EmployeeDto dto) {
        EmployeeDoc doc = EmployeeMapper.toDoc(dto);
        return employeeReactiveRepository.save(doc)
                .map(EmployeeMapper::toDto);
    }

    @Override
    public Mono<EmployeeDto> getEmployeeById(String id) {

        return employeeReactiveRepository.findById(id)
                .map(EmployeeMapper::toDto);
    }

    @Override
    public Flux<EmployeeDto> getAllEmployees() {

        return employeeReactiveRepository.findAll()
                .map(EmployeeMapper::toDto)
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<EmployeeDto> updateEmployee(String id, EmployeeDto employeeDto) {
        Mono<EmployeeDoc> employeeMono = employeeReactiveRepository.findById(id);
        Mono<EmployeeDoc> updatedEmployee = employeeMono.flatMap(employeeDoc -> {
            employeeDoc.setFirstName(employeeDto.getFirstName());
            employeeDoc.setLastName(employeeDto.getLastName());
            employeeDoc.setEmail(employeeDto.getEmail());
            return employeeReactiveRepository.save(employeeDoc);
        });
        return updatedEmployee.map(EmployeeMapper::toDto);
    }
}
