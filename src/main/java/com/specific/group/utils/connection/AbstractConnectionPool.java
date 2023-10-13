package com.specific.group.utils.connection;

import java.util.Map;

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
            case DEFAULT -> result = ConnectionPoolFabric.defaultConnection();
            case FLEXIBLE -> result = ConnectionPoolFabric.flexibleConnection(properties);
            default -> throw new UnsupportedOperationException();
        }
        return result;
    }

}
