package me.ricky.guides.testingguides.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
}
