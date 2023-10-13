package com.specific.group.controller;

import com.specific.group.dao.EmployeeDaoImpl;
import com.specific.group.service.impl.ServiceImpl;

import static com.specific.group.utils.connection.AbstractConnectionPool.Type.DEFAULT;

/**
 * The class provides designer patter factory method for create different controllers.
 */
public class ControllerFactory {

    public static Controller newEmployeeController() {
        return new EmployeeController(new ServiceImpl(new EmployeeDaoImpl.Builder().type(DEFAULT).build()));
    }
}
