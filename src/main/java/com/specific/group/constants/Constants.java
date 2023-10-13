package com.specific.group.constants;

/**
 * Library constants.
 */
public class Constants {

    public final static class Database {
        public static final String SQL_DRIVER = "org.postgresql.Driver";
        public static final Integer DEFAULT_POOL_SIZE = 5;

        public static final String PATH_FILL = "application.properties";

    }

    public final static class Messages {

        public static final String WRONG_ID_VALUE = "Id value must be greater than zero.";
        public static final String WRONG_NAME_VALUE = "Value mustn't be empty";
        public static final String WRONG_ID = "Employee id is not digit.";
        public static final String WRONG_NAME = "Employee value is not letters.";
        public static final String WRONG_DEPARTMENT = "Department value is not digits";
        public static final String WRONG_POSITION = "Position value is not digits.";
    }

    public static class HttpConstants {

        private HttpConstants() {
            throw new UnsupportedOperationException();
        }

        public static class HttpMethod {

            private HttpMethod() {
                throw new UnsupportedOperationException();
            }

            public static final String GET = "GET";
            public static final String POST = "POST";
            public static final String DELETE = "DELETE";
        }

        public static class HttpResponseStatus {

            private HttpResponseStatus() {
                throw new UnsupportedOperationException();
            }

            public static final int STATUS_OK = 200;
            public static final int STATUS_CREATED = 201;
            public static final int STATUS_NO_CONTENT = 204;
            public static final int STATUS_NOT_FOUND = 404;

        }

        public static class UrlPath {

            private UrlPath() {
                throw new UnsupportedOperationException();
            }

            public static final String EMPLOYEE_PATH = "/employee";
        }
    }
}
