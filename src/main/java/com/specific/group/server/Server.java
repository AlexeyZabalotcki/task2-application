package com.specific.group.server;

import com.specific.group.utils.connection.ConnectionPool;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private HttpServer server;
    private final Map<String, Handler> handlers = new HashMap<>();

    public Server(Map<String, Handler> handlers) {
        this.handlers.putAll(handlers);
    }

    /**
     * The method that starts server.
     */
    public void start() {
        try {
            server = HttpServer.create();
            server.bind(new InetSocketAddress(8081), 0);
            handlers.forEach((path, hand) -> server.createContext(path, hand::handle));
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The method that stops server.
     */
    public void stop() {
        ConnectionPool.INSTANCE.destroyPool();
        server.stop(1);
    }
}
