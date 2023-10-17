package com.specific.group.service.impl;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.specific.group.builder.EmployeeBuilder;
import com.specific.group.builder.TestBuilder;
import com.specific.group.dao.Attributes;
import com.specific.group.dao.EmployeeDao;
import com.specific.group.dao.EmployeeDaoImpl;
import com.specific.group.dto.CreateDto;
import com.specific.group.dto.UpdateDto;
import com.specific.group.entity.Employee;
import com.specific.group.service.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static com.specific.group.dao.Attributes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServiceImplTest {

    private static EmployeeDao dao;

    private static Service service;

    private Map<Attributes, String> attributes;
    private List<JsonObject> expected;
    private List<Employee> employees;

    private static Employee employee;

    @BeforeEach
    void setUp() {
        dao = Mockito.mock(EmployeeDaoImpl.class);
        service = new ServiceImpl(dao);
        attributes = new HashMap<>();
        expected = new ArrayList<>();
        employees = new ArrayList<>();

        TestBuilder<Employee> employeeTestBuilder = new EmployeeBuilder();
        employee = employeeTestBuilder.build();

        attributes.put(ID, employee.getId().toString());
        attributes.put(FIRST_NAME, employee.getFirstName());
        attributes.put(LAST_NAME, employee.getLastName());
        attributes.put(DEPARTMENT_ID, employee.getId().toString());
        attributes.put(POSITION_ID, employee.getId().toString());

        employees.add(employee);

        JsonObject json = new JsonObject();
        json.put(ID, employee.getId());
        json.put(FIRST_NAME, employee.getFirstName());
        json.put(LAST_NAME, employee.getLastName());
        json.put(DEPARTMENT_ID, employee.getDepartment().getId());
        json.put(DEPARTMENT_NAME, employee.getDepartment().getName());
        json.put(POSITION_ID, employee.getPosition().getId());
        json.put(POSITION_NAME, employee.getPosition().getName());

        expected.add(json);
    }

    @Test
    void checkSearchShouldReturnListOfEntities() {
        when(dao.search(attributes)).thenReturn(employees);
        List<JsonObject> actual = service.search(attributes);
        assertEquals(expected, actual);
    }

    @Test
    void checkSearchShouldReturnEmptyResponse() {
        employees.remove(0);
        when(dao.search(any())).thenReturn(employees);
        List<JsonObject> actual = service.search(null);
        assertTrue(actual.isEmpty());
        verify(dao, never()).search(any());
    }

    @Test
    void checkCreateShouldReturnNewId() {
        long expected = 2L;
        CreateDto createDto = new CreateDto(employee.getFirstName(),
                                            employee.getLastName(),
                                            employee.getDepartment().getId().toString(),
                                            employee.getDepartment().getId().toString());
        when(dao.create(any())).thenReturn(2L);

        long actual = service.create(createDto);

        assertEquals(expected, actual);
    }

    @Test
    void checkUpdateShouldReturnEntityId() {
        long expected = 0;
        UpdateDto updateDto = new UpdateDto(employee.getId().toString(),
                                            employee.getFirstName(),
                                            employee.getLastName(),
                                            employee.getDepartment().getId().toString(),
                                            employee.getDepartment().getId().toString());
        when(dao.create(any())).thenReturn(1L);

        long actual = service.update(updateDto);

        assertEquals(expected, actual);
    }

    @Test
    void checkDeleteByIdShouldReturnTrue() {
        when(dao.delete(any(Long.class))).thenReturn(true);
        boolean actual = service.deleteById(employee.getId().toString());

        assertTrue(actual);
    }

    @Test
    void checkDeleteByIdShouldReturnFalse() {
        when(dao.delete(any(Long.class))).thenReturn(false);
        boolean actual = service.deleteById(null);

        assertFalse(actual);
    }
}
