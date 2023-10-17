package com.specific.group.builder;

import com.specific.group.entity.Position;

public class PositionBuilder implements TestBuilder<Position> {

    private final Long id = 1L;
    private final String name = "Junior";

    @Override
    public Position build() {
        return new Position.Builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}
