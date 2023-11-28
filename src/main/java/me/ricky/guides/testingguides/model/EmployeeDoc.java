package me.ricky.guides.testingguides.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "employees")
public class EmployeeDoc {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
}
