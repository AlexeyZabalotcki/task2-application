package com.specific.group.server;

import com.specific.group.controller.Controller;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

import static com.specific.group.constants.Constants.HttpConstants.HttpResponseStatus.STATUS_NOT_FOUND;

/**
 * Main handler for all http request and response.
 */
public class Handler {

    private final Controller controller;

    public Handler(Controller controller) {
        this.controller = controller;
    }

    /**
     * The main method process httpExchange.
     *
     * @param httpExchange a http exchange.
     */
    public void handle(final HttpExchange httpExchange) {
        try {
            controller.execute(httpExchange);
        } catch (final Exception e) {
            handleException(httpExchange);
        } finally {
            httpExchange.close();
        }
    }

    private void handleException(final HttpExchange httpExchange) {
        try {
            httpExchange.sendResponseHeaders(STATUS_NOT_FOUND, 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
