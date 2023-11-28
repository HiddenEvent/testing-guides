package me.ricky.guides.testingguides.repository;

import me.ricky.guides.testingguides.model.EmployeeDoc;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EmployeeDocRepository extends ReactiveCrudRepository<EmployeeDoc, String> {
}
