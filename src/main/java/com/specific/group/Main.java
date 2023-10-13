package com.specific.group;

import com.specific.group.controller.ControllerFactory;
import com.specific.group.server.Handler;
import com.specific.group.server.Server;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.specific.group.constants.Constants.HttpConstants.UrlPath.EMPLOYEE_PATH;

public class Main {

    public static void main(String[] args) {

        Map<String, Handler> handlers = new HashMap<>();
        handlers.put(EMPLOYEE_PATH, new Handler(ControllerFactory.newEmployeeController()));
        Server server = new Server(handlers);
        server.start();
        boolean close = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press 1 for stop server");

        while (!close) {
            String input = scanner.next();
            if ("1".equals(input)) {
                server.stop();
                close = true;
            }
        }
    }
}