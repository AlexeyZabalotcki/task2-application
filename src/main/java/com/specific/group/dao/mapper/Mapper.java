package com.specific.group.dao.mapper;

import com.specific.group.entity.Department;
import com.specific.group.entity.Employee;
import com.specific.group.entity.Position;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Mapper {
    public static Employee mapEmployee(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        Long departmentId = resultSet.getLong("department_id");
        String departmentName = resultSet.getString("department_name");
        Long positionId = resultSet.getLong("position_id");
        String positionName = resultSet.getString("position_name");

        Department department = new Department.Builder()
                .id(departmentId)
                .name(departmentName)
                .build();

        Position position = new Position.Builder()
                .id(positionId)
                .name(positionName)
                .build();

        return new Employee.Builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .department(department)
                .position(position)
                .build();
    }
}
