package com.specific.group.utils.jdbc;

import com.specific.group.dao.Attributes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import static com.specific.group.dao.Sql.Select.*;

/**
 * Util class which provides methods for create requests to the database
 */
public class JdbcUtils {
    public static void setStatement(PreparedStatement preparedStatement, Object[] seq) throws SQLException {
        for (int i = 1; i < seq.length + 1; i++) {
            preparedStatement.setObject(i, seq[i - 1]);
        }
    }

    public static void addQueryParams(Map<Attributes, String> attributes, StringBuilder sqlBuilder) {
        if (attributes != null && !attributes.isEmpty()) {
            sqlBuilder.append(WHERE);
            for (Map.Entry<Attributes, String> entry : attributes.entrySet()) {
                Attributes attribute = entry.getKey();

                switch (attribute) {
                    case ID -> sqlBuilder.append(SELECT_ID);
                    case FIRST_NAME -> sqlBuilder.append(SELECT_FIRST_NAME);
                    case LAST_NAME -> sqlBuilder.append(SELECT_LAST_NAME);
                    case DEPARTMENT_ID -> sqlBuilder.append(SELECT_DEPARTMENT_ID);
                    case POSITION_ID -> sqlBuilder.append(SELECT_POSITION_ID);
                }

                sqlBuilder.append(SELECT_AND);
            }

            sqlBuilder.setLength(sqlBuilder.length() - 5);
        }
    }

}
