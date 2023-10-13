package com.specific.group.entity;

import java.util.Objects;

import static com.specific.group.constants.Constants.Messages.WRONG_ID_VALUE;
import static com.specific.group.constants.Constants.Messages.WRONG_NAME_VALUE;

public class Employee {

    private Long id;
    private String firstName;
    private String lastName;
    private Department department;
    private Position position;

    private Employee(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.department = builder.department;
        this.position = builder.position;
    }

    /**
     * Returns the ID of the employee.
     *
     * @return the ID of the employee
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the first name of the employee.
     *
     * @return the first name of the employee
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the employee.
     *
     * @return the last name of the employee
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the department of the employee.
     *
     * @return the department of the employee
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Returns the position of the employee.
     *
     * @return the position of the employee
     */
    public Position getPosition() {
        return position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(department, employee.department) &&
                Objects.equals(position, employee.position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, department, position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department=" + department +
                ", position=" + position +
                '}';
    }

    /**
     * Builder class for creating instances of the Employee class.
     */
    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private Department department;
        private Position position;

        /**
         * Sets the ID of the employee.
         *
         * @param id the ID of the employee
         * @return the Builder instance
         */
        public Builder id(Long id) {
            if (id > 0) {
                this.id = id;
            } else {
                throw new IllegalArgumentException(WRONG_ID_VALUE);
            }

            return this;
        }

        /**
         * Sets the first name of the employee.
         *
         * @param firstName the first name of the employee
         * @return the Builder instance
         */
        public Builder firstName(String firstName) {
            if (firstName != null && !firstName.isEmpty() && !" ".equals(firstName)) {
                this.firstName = firstName;
            } else {
                throw new IllegalArgumentException(WRONG_NAME_VALUE);
            }

            return this;
        }

        /**
         * Sets the last name of the employee.
         *
         * @param lastName the last name of the employee
         * @return the Builder instance
         */
        public Builder lastName(String lastName) {
            if (lastName != null && !lastName.isEmpty() && !" ".equals(lastName)) {
                this.lastName = lastName;
            } else {
                throw new IllegalArgumentException(WRONG_NAME_VALUE);
            }

            return this;
        }

        /**
         * Sets the department of the employee.
         *
         * @param department the department of the employee
         * @return the Builder instance
         */
        public Builder department(Department department) {
            this.department = department;
            return this;
        }

        /**
         * Sets the position of the employee.
         *
         * @param position the position of the employee
         * @return the Builder instance
         */
        public Builder position(Position position) {
            this.position = position;
            return this;
        }

        /**
         *Continuing from the previous response:

         ```java
         * Builds an instance of the Employee class with the specified attributes.
         *
         * @return an instance of the Employee class
         */
        public Employee build() {
            return new Employee(this);
        }
    }

}
