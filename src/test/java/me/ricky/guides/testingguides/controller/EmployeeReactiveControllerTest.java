package me.ricky.guides.testingguides.controller;

import me.ricky.guides.testingguides.dto.EmployeeDto;
import me.ricky.guides.testingguides.service.EmployeeReactiveService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeReactiveController.class)
class EmployeeReactiveControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private EmployeeReactiveService employeeReactiveService;

    @Test
    void saveEmployee() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("Ricky")
                .lastName("Kim")
                .email("aa@a.com")
                .build();

        BDDMockito.given(employeeReactiveService.saveEmployee(ArgumentMatchers.any(EmployeeDto.class)))
                .willReturn(Mono.just(employeeDto));

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
        String employeeId = "123";
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("Ricky")
                .lastName("Kim")
                .email("aa@a.com")
                .build();
        BDDMockito.given(employeeReactiveService.getEmployeeById(employeeId))
                .willReturn(Mono.just(employeeDto));

        //when
        WebTestClient.ResponseSpec response = webTestClient.get()
                .uri(EmployeeReactiveController.BASE_URL + "/" + employeeId)
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
        BDDMockito.given(employeeReactiveService.getAllEmployees())
                .willReturn(Mono.just(employeeDto).flux().concatWith(Mono.just(employeeDto1).flux()));

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
                .jsonPath("$.[0].firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.[0].lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.[0].email").isEqualTo(employeeDto.getEmail())
                .jsonPath("$.[1].firstName").isEqualTo(employeeDto1.getFirstName())
                .jsonPath("$.[1].lastName").isEqualTo(employeeDto1.getLastName())
                .jsonPath("$.[1].email").isEqualTo(employeeDto1.getEmail());

    }

    @Test
    void updateEmployee() {
    }

    @Test
    void deleteEmployee() {
    }
}