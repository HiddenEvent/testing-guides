package me.ricky.guides.testingguides.service;

import lombok.RequiredArgsConstructor;
import me.ricky.guides.testingguides.dto.EmployeeDto;
import me.ricky.guides.testingguides.mapper.EmployeeMapper;
import me.ricky.guides.testingguides.model.EmployeeDoc;
import me.ricky.guides.testingguides.repository.EmployeeReactiveRepository;
import org.springframework.stereotype.Service;
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
}
