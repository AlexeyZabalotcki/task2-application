package com.specific.group.builder;

import com.specific.group.entity.Department;

public class DepartmentBuilder implements TestBuilder<Department> {

    private final Long id = 1L;
    private final String name = "Department Java";

    @Override
    public Department build() {
        return new Department.Builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}
