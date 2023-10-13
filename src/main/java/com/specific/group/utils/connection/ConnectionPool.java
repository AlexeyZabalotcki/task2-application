package com.specific.group.utils.connection;


import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.specific.group.constants.Constants.Database.DEFAULT_POOL_SIZE;
import static com.specific.group.constants.Constants.Database.SQL_DRIVER;

public enum ConnectionPool {

    INSTANCE();


    private String url;
    private String password;
    private String username;
    private String poolSize;

    private BlockingQueue<Connection> pool;
    private List<Connection> sourceConnection;

    ConnectionPool() {
    }

    ConnectionPool passwordKey(String passwordKey) {
        this.password = passwordKey;
        return this;
    }

    ConnectionPool usernameKey(String usernameKey) {
        this.username = usernameKey;
        return this;
    }

    ConnectionPool urlKey(String urlKey) {
        this.url = urlKey;
        return this;
    }

    ConnectionPool poolSize(String poolSize) {
        this.poolSize = poolSize;
        return this;
    }

    ConnectionPool build() {
        loadDriver();
        initConnectionPool();
        return this;
    }

    private void initConnectionPool() {
        String poolSizeValue = poolSize;
        int size = poolSizeValue == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSizeValue);
        pool = new ArrayBlockingQueue<>(size);
        sourceConnection = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            createProxyConnection();
        }
    }

    public Connection openConnection() {
        Connection connection = null;
        try {
            connection = pool.poll(1, TimeUnit.SECONDS);
            if (!(connection != null && connection.isValid(1))) {
                createProxyConnection();
                connection = openConnection();
            }
        } catch (InterruptedException | SQLException e) {
            Thread.currentThread().interrupt();
        }
        return connection;
    }


    private void createProxyConnection() {
        final Connection connection;
        try {
            connection = postgreSqlDataSource().getConnection();
            Object proxyConnection = Proxy.newProxyInstance(ConnectionPool.class.getClassLoader(), new Class[]{Connection.class},
                    (proxy, method, args) -> method.getName().equals("close") ? pool.add((Connection) proxy) : method.invoke(connection, args));
            pool.add((Connection) proxyConnection);
            sourceConnection.add(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroyPool() {
        for (Connection connection : sourceConnection) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private DataSource postgreSqlDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    private void loadDriver() {
        try {
            Class.forName(SQL_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
