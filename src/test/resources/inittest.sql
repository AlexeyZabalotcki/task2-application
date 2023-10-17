DROP SCHEMA IF EXISTS employee CASCADE;

CREATE SCHEMA employee;

DROP TABLE IF EXISTS employee.employee;
DROP TABLE IF EXISTS employee.department;
DROP TABLE IF EXISTS employee.position;

CREATE TABLE IF NOT EXISTS employee.department
(
    id   SERIAL PRIMARY KEY,
    department_name VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS employee.position
(
    id   SERIAL PRIMARY KEY,
    position_name VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS employee.employee
(
    id            SERIAL PRIMARY KEY,
    first_name    VARCHAR(128) NOT NULL,
    last_name     VARCHAR(128) NOT NULL,
    department_id INTEGER REFERENCES employee.department (id) ON DELETE SET NULL,
    position_id   INTEGER REFERENCES employee.position (id) ON DELETE SET NULL
);

INSERT INTO employee.department (department_name)
VALUES ('Department Java'),
       ('Department JS'),
       ('Department C#');

INSERT INTO employee.position (position_name)
VALUES ('Junior'),
       ('Middle'),
       ('Senior');

INSERT INTO employee.employee (first_name, last_name, department_id, position_id)
VALUES ('Alexey', 'Zabalotsky', 1, 1),
       ('Vladislav', 'Savko', 2, 2),
       ('Viktor', 'Sinkevich', 3, 3);