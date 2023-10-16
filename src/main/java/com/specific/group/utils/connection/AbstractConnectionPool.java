package com.specific.group.utils.connection;

import java.util.Map;

/**
 * Provides design pattern abstract method for create connection pool
 */
public class AbstractConnectionPool {
    private AbstractConnectionPool() {
        throw new UnsupportedOperationException();
    }

    public enum Type {
        DEFAULT,
        FLEXIBLE
    }

    public static ConnectionPool connectionPool(Type type, Map<String, String> properties) {
        ConnectionPool result;
        switch (type) {
            case DEFAULT -> result = ConnectionPoolFactory.defaultConnection();
            case FLEXIBLE -> result = ConnectionPoolFactory.flexibleConnection(properties);
            default -> throw new UnsupportedOperationException();
        }
        return result;
    }

}
