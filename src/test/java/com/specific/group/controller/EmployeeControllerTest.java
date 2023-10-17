package com.specific.group.controller;

import com.specific.group.server.Handler;
import com.specific.group.server.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;

import static com.specific.group.constants.Constants.HttpConstants.HttpMethod.*;
import static com.specific.group.constants.Constants.HttpConstants.HttpResponseStatus.*;
import static com.specific.group.constants.Constants.HttpConstants.UrlPath.EMPLOYEE_PATH;
import static com.specific.group.controller.ControllerFactory.newFlaxibleEmployeeController;
import static com.specific.group.utils.connection.ConnectionPoolFactory.PropertiesFile.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeControllerTest {

    @Container
    private static final PostgreSQLContainer<BaseIntegrationTest> postgreSQLContainer = BaseIntegrationTest.getContainer();
    private static Server server;

    @BeforeAll
    void setUp() {
        postgreSQLContainer.start();
        Map<String, String> attributes = new HashMap<>();
        attributes.put(PASSWORD_KEY, postgreSQLContainer.getPassword());
        attributes.put(USERNAME_KEY, postgreSQLContainer.getUsername());
        attributes.put(URL_KEY, postgreSQLContainer.getJdbcUrl());
        attributes.put(POOL_SIZE, "4");
        Map<String, Handler> capacitorPath = new HashMap<>();
        capacitorPath.put(EMPLOYEE_PATH, new Handler(newFlaxibleEmployeeController(attributes)));
        server = new Server(capacitorPath);
        server.start();
    }

    @AfterAll
    void stop() {
        server.stop();
        postgreSQLContainer.stop();
    }

    @Test
    void checkSearchIntegrationTest() {
        String expected = "{\"departmentName\":\"Department Java\",\"positionName\":\"Junior\",\"firstName\":\"Alexey\"," +
                "\"lastName\":\"Zabalotsky\",\"positionId\":1,\"departmentId\":1,\"id\":1}";

        Client client = new Client.Builder()
                .url("http://localhost:8080/employee")
                .getRequestUrl("/search?id=1")
                .requestMethod(GET)
                .build();
        client.sendRequest();
        assertAll(() -> {
            assertEquals(expected, client.getResponse());
            assertEquals(STATUS_OK, client.getResponseCode());
        });
    }

    @Test
    void checkCreateShouldReturn201StatusCode() {
        String content = "{\n" +
                "    \"firstName\":\"Ivan\",\n" +
                "    \"lastName\":\"Kozlov\",\n" +
                "    \"departmentId\": \"1\",\n" +
                "    \"positionId\": \"2\"\n" +
                "}";
        Client client = new Client.Builder()
                .url("http://localhost:8080/employee/create")
                .requestMethod(POST)
                .content(content)
                .build();
        client.sendRequest();
        assertAll(() -> {
            assertEquals(STATUS_CREATED, client.getResponseCode());
        });
    }

    @Test
    void checkUpdateShouldReturnExpectedIdAnd201StatusCode() {
        String content = "{\n" +
                "    \"id\":\"3\",\n" +
                "    \"firstName\":\"Ivan\",\n" +
                "    \"lastName\":\"Kozlov\",\n" +
                "    \"departmentId\": \"1\",\n" +
                "    \"positionId\": \"3\"\n" +
                "}";
        Client client = new Client.Builder()
                .url("http://localhost:8080/employee/update")
                .requestMethod(POST)
                .content(content)
                .build();
        client.sendRequest();
        String expected = "{\"id\":3}";
        assertAll(() -> {
            assertEquals(STATUS_CREATED, client.getResponseCode());
            assertEquals(expected, client.getResponse());
        });
    }

    @Test
    void checkDeleteShouldReturn204StatusCode() {
        Client client = new Client.Builder()
                .url("http://localhost:8080/employee/delete?id=2")
                .requestMethod(DELETE)
                .build();
        client.sendRequest();
        assertEquals(STATUS_NO_CONTENT, client.getResponseCode());
    }
}
