package com.specific.group.dao;


/**
 * Class for Sql request to database.
 */
public class Sql {
    private Sql() {
        throw new UnsupportedOperationException();
    }

    public final static class Select {
        private Select() {
            throw new UnsupportedOperationException();
        }

        public static final String SELECT = """
                SELECT e.id, e.first_name, e.last_name, e.department_id, d.department_name, e.position_id, p.position_name
                FROM employee.employee e
                         JOIN employee.department d ON d.id = e.department_id
                         JOIN employee.position p ON p.id = e.position_id
                """;
        public static final String SELECT_ID = "e.id=?";
        public static final String SELECT_FIRST_NAME = "e.first_name=?";
        public static final String SELECT_LAST_NAME = "e.last_name=?";
        public static final String SELECT_DEPARTMENT_ID = "e.department_id = ?";
        public static final String SELECT_POSITION_ID = "e.position_id = ?";
        public static final String SELECT_AND = " AND ";
        public static final String WHERE = " AND ";


    }

    final static class Insert {
        private Insert() {
            throw new UnsupportedOperationException();
        }

        static final String INSERT = """
                INSERT INTO employee.employee (first_name, last_name, department_id, position_id)
                VALUES (?,?,?,?)""";
    }

    final static class Update {
        private Update() {
            throw new UnsupportedOperationException();
        }

        static final String UPDATE = """
                UPDATE employee.employee
                SET
                """;
        static final String UPDATE_FIRST_NAME = "first_name=?";
        static final String UPDATE_LAST_NAME = "last_name=?";
        static final String UPDATE_DEPARTMENT_ID = "department_id=?";
        static final String UPDATE_POSITION_ID = "position_id=?";
        static final String UPDATE_COMMA = ",";
        static final String UPDATE_WHERE_ID = " WHERE id=?";
    }

    final static class Delete {
        private Delete() {
            throw new UnsupportedOperationException();
        }

        static final String DELETE_BY_ID = "DELETE FROM employee.employee WHERE id=?";
    }

    final static class Tables {
        private Tables() {
            throw new UnsupportedOperationException();
        }

        static final String TABLE_EMPLOYEE = "employee.employee";
        static final String TABLE_DEPARTMENT = "employee.department";
        static final String TABLE_POSITION = "employee.position";
    }

    public final static class Constant {

        private Constant() { throw new UnsupportedOperationException();}

        public static final long INVALID_RESULT = -1;
    }
}
