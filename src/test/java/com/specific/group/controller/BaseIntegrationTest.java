package com.specific.group.controller;

import com.specific.group.utils.connection.AbstractConnectionPool;
import com.specific.group.utils.connection.ConnectionPool;
import com.specific.group.utils.connection.ConnectionPoolFactory;
import org.jetbrains.annotations.NotNull;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static com.specific.group.utils.connection.AbstractConnectionPool.Type.FLEXIBLE;
import static com.specific.group.utils.connection.ConnectionPoolFactory.PropertiesFile.PASSWORD_KEY;
import static com.specific.group.utils.connection.ConnectionPoolFactory.PropertiesFile.POOL_SIZE;
import static com.specific.group.utils.connection.ConnectionPoolFactory.PropertiesFile.URL_KEY;
import static com.specific.group.utils.connection.ConnectionPoolFactory.PropertiesFile.USERNAME_KEY;

public class BaseIntegrationTest extends PostgreSQLContainer<BaseIntegrationTest> {

    private static final Map<String, String> attributes = new HashMap<>();
    private static ConnectionPool connectionPool;
    private static BaseIntegrationTest container;

    static {
        container = new BaseIntegrationTest();
        container
                .withInitScript("inittest.sql");
        container.start();
    }

    private BaseIntegrationTest() {
        super("postgres:13");
    }

    @Override
    public void start() {
        super.start();
        attributes.put(PASSWORD_KEY, container.getPassword());
        attributes.put(USERNAME_KEY, container.getUsername());
        attributes.put(URL_KEY, container.getJdbcUrl());
        attributes.put(POOL_SIZE, "4");
        connectionPool = AbstractConnectionPool.connectionPool(FLEXIBLE, attributes);
        connectionPool = ConnectionPoolFactory.flexibleConnection(attributes);
        try (Connection connection = connectionPool.openConnection();
             Statement statement = connection.createStatement()) {
            String initSql = readScripts("inittest.sql");
            statement.executeUpdate(initSql);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @NotNull
    private String readScripts(String fileName) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        String initSql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        return initSql;
    }


    @Override
    public void stop() {
        super.stop();
        connectionPool.destroyPool();
    }

    public static BaseIntegrationTest getContainer() {
        if (container == null) {
            container = new BaseIntegrationTest() {
            };
        }
        return container;
    }
}
