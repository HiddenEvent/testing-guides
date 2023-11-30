package me.ricky.guides.testingguides.controller;

import me.ricky.guides.testingguides.dto.EmployeeDto;
import me.ricky.guides.testingguides.repository.EmployeeReactiveRepository;
import me.ricky.guides.testingguides.service.EmployeeReactiveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeReactiveControllerIT {
    @Autowired
    private EmployeeReactiveService employeeReactiveService;
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private EmployeeReactiveRepository employeeReactiveRepository;

    @BeforeEach
    void setup() {
        System.out.println("setup");
        employeeReactiveRepository.deleteAll().subscribe();
    }

    @Test
    void saveEmployee() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("Ricky")
                .lastName("Kim")
                .email("aa@a.com")
                .build();

        //when
        WebTestClient.ResponseSpec response = webTestClient.post().uri(EmployeeReactiveController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange();

        //then
        response.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());

    }

    @Test
    void getEmployeeById() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("Ricky")
                .lastName("Kim")
                .email("aa@a.com")
                .build();
        EmployeeDto savedEmployee = employeeReactiveService.saveEmployee(employeeDto).block();

        //when
        WebTestClient.ResponseSpec response = webTestClient.get()
                .uri(EmployeeReactiveController.BASE_URL + "/{id}", savedEmployee.getId())
                .exchange();

        //then
        response.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(savedEmployee.getId())
                .jsonPath("$.firstName").isEqualTo(savedEmployee.getFirstName())
                .jsonPath("$.lastName").isEqualTo(savedEmployee.getLastName())
                .jsonPath("$.email").isEqualTo(savedEmployee.getEmail());

    }

    @Test
    void getAllEmployees() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("Ricky")
                .lastName("Kim")
                .email("aa@a.com")
                .build();
        EmployeeDto employeeDto1 = EmployeeDto.builder()
                .firstName("Ricky1")
                .lastName("Kim1")
                .email("aa1@a.com")
                .build();
        EmployeeDto savedEmployee = employeeReactiveService.saveEmployee(employeeDto).block();
        EmployeeDto savedEmployee1 = employeeReactiveService.saveEmployee(employeeDto1).block();

        //when
        WebTestClient.ResponseSpec response = webTestClient.get()
                .uri(EmployeeReactiveController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        //then
        response.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.size()").isEqualTo(2)
                .jsonPath("$.[0].id").isEqualTo(savedEmployee.getId())
                .jsonPath("$.[0].firstName").isEqualTo(savedEmployee.getFirstName())
                .jsonPath("$.[0].lastName").isEqualTo(savedEmployee.getLastName())
                .jsonPath("$.[0].email").isEqualTo(savedEmployee.getEmail())
                .jsonPath("$.[1].id").isEqualTo(savedEmployee1.getId())
                .jsonPath("$.[1].firstName").isEqualTo(savedEmployee1.getFirstName())
                .jsonPath("$.[1].lastName").isEqualTo(savedEmployee1.getLastName())
                .jsonPath("$.[1].email").isEqualTo(savedEmployee1.getEmail());

    }

    @Test
    void updateEmployee() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("Ricky")
                .lastName("Kim")
                .email("aa@a.com")
                .build();
        EmployeeDto savedEmployee = employeeReactiveService.saveEmployee(employeeDto).block();
        EmployeeDto updatedEmployeeDto = EmployeeDto.builder()
                .firstName("UPDATED Ricky")
                .lastName("UPDATED Kim")
                .email("upd@a.com")
                .build();


        //when
        WebTestClient.ResponseSpec response = webTestClient.put()
                .uri(EmployeeReactiveController.BASE_URL + "/{id}", savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(updatedEmployeeDto), EmployeeDto.class)
                .exchange();

        //then
        response.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(savedEmployee.getId())
                .jsonPath("$.firstName").isEqualTo(updatedEmployeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(updatedEmployeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(updatedEmployeeDto.getEmail());
    }

    @Test
    void deleteEmployee() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("Ricky")
                .lastName("Kim")
                .email("aa@a.com")
                .build();
        EmployeeDto savedEmployee = employeeReactiveService.saveEmployee(employeeDto).block();

        //when
        WebTestClient.ResponseSpec response = webTestClient.delete()
                .uri(EmployeeReactiveController.BASE_URL + "/{id}", savedEmployee.getId())
                .exchange();

        //then
        response.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);
    }
}