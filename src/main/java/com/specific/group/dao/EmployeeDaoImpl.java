package com.specific.group.dao;

import com.specific.group.dao.mapper.Mapper;
import com.specific.group.dto.CreateDto;
import com.specific.group.dto.UpdateDto;
import com.specific.group.entity.Employee;
import com.specific.group.utils.connection.AbstractConnectionPool;
import com.specific.group.utils.connection.ConnectionPool;
import com.specific.group.utils.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.specific.group.dao.Sql.Constant.INVALID_RESULT;
import static com.specific.group.dao.Sql.Delete.DELETE_BY_ID;
import static com.specific.group.dao.Sql.Insert.INSERT;
import static com.specific.group.dao.Sql.Select.SELECT;
import static com.specific.group.dao.Sql.Select.SELECT_FIRST_NAME;
import static com.specific.group.dao.Sql.Select.SELECT_ID;
import static com.specific.group.dao.Sql.Select.SELECT_LAST_NAME;
import static com.specific.group.dao.Sql.Tables.TABLE_DEPARTMENT;
import static com.specific.group.dao.Sql.Tables.TABLE_EMPLOYEE;
import static com.specific.group.dao.Sql.Tables.TABLE_POSITION;
import static com.specific.group.dao.Sql.Update.UPDATE;
import static com.specific.group.dao.Sql.Update.UPDATE_COMMA;
import static com.specific.group.dao.Sql.Update.UPDATE_DEPARTMENT_ID;
import static com.specific.group.dao.Sql.Update.UPDATE_FIRST_NAME;
import static com.specific.group.dao.Sql.Update.UPDATE_LAST_NAME;
import static com.specific.group.dao.Sql.Update.UPDATE_POSITION_ID;
import static com.specific.group.dao.Sql.Update.UPDATE_WHERE_ID;

/**
 * {@inheritDoc}
 */
public class EmployeeDaoImpl implements EmployeeDao {

    private static ConnectionPool connectionPool;

    private EmployeeDaoImpl(final Builder builder) {
        if (connectionPool == null) {
            connectionPool = AbstractConnectionPool.connectionPool(builder.type, builder.properties);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> search(Map<Attributes, String> attributes) {
        String sql = SELECT.formatted(SELECT_ID, SELECT_FIRST_NAME, SELECT_LAST_NAME, TABLE_DEPARTMENT, TABLE_POSITION);
        List<Employee> employees = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder(sql);

        JdbcUtils.addQueryParams(attributes, sqlBuilder);

        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString())) {

            createPreparedStatement(attributes, preparedStatement);

            getResultSet(preparedStatement, employees);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employees;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long create(CreateDto request) {
        long result = INVALID_RESULT;
        String command = INSERT.formatted(TABLE_EMPLOYEE);
        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {
            Object[] seq = {
                    request.firstName(),
                    request.lastName(),
                    Integer.parseInt(request.department()),
                    Integer.parseInt(request.position())
            };
            JdbcUtils.setStatement(preparedStatement, seq);
            preparedStatement.execute();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    result = generatedKeys.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long update(UpdateDto employeeDto) {
        long result = INVALID_RESULT;
        String sql = UPDATE.formatted(TABLE_EMPLOYEE);
        StringBuilder sqlBuilder = new StringBuilder(sql);
        sqlBuilder.append(UPDATE_FIRST_NAME).append(UPDATE_COMMA)
                .append(UPDATE_LAST_NAME).append(UPDATE_COMMA)
                .append(UPDATE_DEPARTMENT_ID).append(UPDATE_COMMA)
                .append(UPDATE_POSITION_ID)
                .append(UPDATE_WHERE_ID);
        Object[] req = {
                employeeDto.firstName(),
                employeeDto.lastName(),
                Integer.parseInt(employeeDto.department()),
                Integer.parseInt(employeeDto.position()),
                Long.parseLong(employeeDto.id())
        };
        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString())) {
            JdbcUtils.setStatement(preparedStatement, req);
            result = preparedStatement.executeUpdate() == 1 ? Long.parseLong(employeeDto.id()) : result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(long id) {
        int result;
        String delete = DELETE_BY_ID.formatted(TABLE_EMPLOYEE);
        try (Connection connection = connectionPool.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setLong(1, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result == 1;
    }

    private static void getResultSet(PreparedStatement preparedStatement, List<Employee> employees) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                employees.add(Mapper.mapEmployee(resultSet));
            }
        }
    }

    private void createPreparedStatement(Map<Attributes, String> attributes, PreparedStatement preparedStatement) throws SQLException {
        if (attributes != null && !attributes.isEmpty()) {
            int index = 1;
            for (String value : attributes.values()) {
                if (isNumeric(value)) {
                    preparedStatement.setInt(index, Integer.parseInt(value));
                } else {
                    preparedStatement.setString(index, value);
                }
                index++;
            }
        }
    }


    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * Builder sets setting for EmployeeDaoImpl
     */
    public static class Builder {

        private final Map<String, String> properties = new HashMap<>();
        private AbstractConnectionPool.Type type;

        /**
         * Provides method that set properties for EmployeeDaoImpl
         *
         * @param key   that key need to use from PropertiesFile
         * @param value a string value
         * @return itself
         */
        public Builder property(String key, String value) {
            properties.put(key, value);
            return this;
        }

        /**
         * Provides method that set properties for EmployeeDaoImpl
         *
         * @param properties a set of the properties
         * @return itself
         */
        public Builder property(Map<String, String> properties) {
            this.properties.putAll(properties);
            return this;
        }

        /**
         * Provides method that set type for loading EmployeeDaoImpl
         *
         * @param type a type of AbstractConnectionPool.Type
         * @return itself
         */
        public Builder type(AbstractConnectionPool.Type type) {
            this.type = type;
            return this;
        }

        /**
         * Create EmployeeDaoImpl
         *
         * @return a EmployeeDaoImpl interface
         */
        public EmployeeDao build() {
            return new EmployeeDaoImpl(this);
        }

    }

}
