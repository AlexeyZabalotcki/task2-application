package com.specific.group.entity;

import java.util.Objects;

import static com.specific.group.constants.Constants.Messages.WRONG_ID_VALUE;
import static com.specific.group.constants.Constants.Messages.WRONG_NAME_VALUE;


/**
 * The Department entity
 */
public class Department {

    private Long id;
    private String name;

    private Department(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }


    /**
     * Returns an id of the department.
     *
     * @return a department id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns a name of the department.
     *
     * @return a department id.
     */
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }


    /**
     * Wraps and builds the instance of the department entity
     */
    public final static class Builder {
        private Long id;
        private String name;

        /**
         * Constructs a department model based on the department object.
         *
         * @param id a department model.
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
         * Constructs a department model based on the department object.
         *
         * @param name a Department model.
         */
        public Builder name(String name) {
            if (name != null && !name.isEmpty() && !" ".equals(name)) {
                this.name = name;
            } else {
                throw new IllegalArgumentException(WRONG_NAME_VALUE);
            }

            return this;
        }

        /**
         * Builds a Department entity with required parameters.
         *
         * @return a builder of the Department entity.
         */
        public Department build() {
            return new Department(this);
        }
    }
}
