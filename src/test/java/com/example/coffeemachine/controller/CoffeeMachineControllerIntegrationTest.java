package com.example.coffeemachine.controller;

import com.example.coffeemachine.CoffeeMachineApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {CoffeeMachineApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {CoffeeMachineControllerIntegrationTest.Initializer.class})
@Testcontainers
@Transactional
class CoffeeMachineControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("coffeemachinedb")
            .withUsername("postgres")
            .withPassword("postgres");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    void createCoffeeMachineTest() throws Exception {
        String json = """
                {
                    "name" : "test_machine",
                    "isOn": true,
                    "capacity": 12,
                    "coffeeLevel": 3
                }
                """;

        this.mockMvc.perform(post("/api/v1/coffee-machines/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void createCoffeeMachineTestMachineAlreadyExist() throws Exception {
        String json = """
                {
                    "name" : "first_machine",
                    "isOn": true,
                    "capacity": 12,
                    "coffeeLevel": 3
                }
                """;

        this.mockMvc.perform(post("/api/v1/coffee-machines/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCoffeeMachineTestAbsentName() throws Exception {
        String json = """
                {
                    "isOn": true,
                    "capacity": 12,
                    "coffeeLevel": 3
                }
                """;

        this.mockMvc.perform(post("/api/v1/coffee-machines/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}