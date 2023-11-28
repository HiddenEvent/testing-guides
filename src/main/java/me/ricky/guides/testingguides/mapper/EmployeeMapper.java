package me.ricky.guides.testingguides.mapper;

import me.ricky.guides.testingguides.dto.EmployeeDto;
import me.ricky.guides.testingguides.model.EmployeeDoc;

public class EmployeeMapper {
    public static EmployeeDto toDto(EmployeeDoc employeeDoc) {
        return EmployeeDto.builder()
                .id(employeeDoc.getId())
                .firstName(employeeDoc.getFirstName())
                .lastName(employeeDoc.getLastName())
                .email(employeeDoc.getEmail())
                .build();
    }
    public static EmployeeDoc toDoc(EmployeeDto employeeDto) {
        return EmployeeDoc.builder()
                .id(employeeDto.getId())
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .email(employeeDto.getEmail())
                .build();
    }
}
