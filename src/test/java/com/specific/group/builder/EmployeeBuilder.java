package com.specific.group.builder;

import com.specific.group.entity.Department;
import com.specific.group.entity.Employee;
import com.specific.group.entity.Position;

public class EmployeeBuilder implements TestBuilder<Employee> {

    private final Long id = 1L;
    private final String firstName = "Alexey";
    private final String lastName = "Zabolotsky";
    private final Department department = new DepartmentBuilder().build();
    private final Position position = new PositionBuilder().build();

    @Override
    public Employee build() {
        return new Employee.Builder()
                .id(this.id)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .department(this.department)
                .position(this.position)
                .build();
    }
}
