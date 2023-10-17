package com.specific.group.controller;

import com.specific.group.dao.EmployeeDaoImpl;
import com.specific.group.service.impl.ServiceImpl;

import java.util.Map;

import static com.specific.group.utils.connection.AbstractConnectionPool.Type.DEFAULT;
import static com.specific.group.utils.connection.AbstractConnectionPool.Type.FLEXIBLE;

/**
 * The class provides designer patter factory method for create different controllers.
 */
public class ControllerFactory {

    public static Controller newEmployeeController() {
        return new EmployeeController(new ServiceImpl(new EmployeeDaoImpl.Builder().type(DEFAULT).build()));
    }

    public static Controller newFlaxibleEmployeeController(Map<String, String> properties) {
        return new EmployeeController(new ServiceImpl(new EmployeeDaoImpl.Builder().type(FLEXIBLE).property(properties).build()));
    }
}

