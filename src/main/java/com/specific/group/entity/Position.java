package com.specific.group.entity;

import java.util.Objects;

import static com.specific.group.constants.Constants.Messages.WRONG_ID_VALUE;
import static com.specific.group.constants.Constants.Messages.WRONG_NAME_VALUE;

public class Position {

    private Long id;
    private String name;

    private Position(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    /**
     * Returns an id of the Position.
     *
     * @return a Position id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns a name of the Position.
     *
     * @return a Position name.
     */
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(id, position.id) && Objects.equals(name, position.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * Wraps and builds the instance of the Position entity
     */
    public final static class Builder {
        private Long id;
        private String name;

        /**
         * Constructs a Position model based on the Position object.
         *
         * @param id a Position model.
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
         * Constructs a Position model based on the department object.
         *
         * @param name a Position model.
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
         * Builds a Position entity with required parameters.
         *
         * @return a builder of the Position entity.
         */
        public Position build() {
            return new Position(this);
        }
    }
}
