package com.specific.group.dao;

import com.specific.group.dto.CreateDto;
import com.specific.group.dto.UpdateDto;
import com.specific.group.entity.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeDao {

    /**
     * Search strings in element table.
     * @param attributes represent criteria for search element
     * @return a list of element which will be found
     */
    List<Employee> search(Map<Attributes, String> attributes);
    /**
     * Create new string in element table.
     * @param request a createDto object which contain fields that need to fill for element table.
     * @return a long provides id element in element table
     */
    long create(CreateDto request);

    /**
     * Update string in element table.
     * @param request a createDto object which contain fields that need to update for element table.
     * @return a long provides id element in element table
     */
    long update(UpdateDto request);

    /**
     * Delete string in element table.
     * @param id a long is id in db, whose line should delete
     * @return provides whether the request was successful
     */
    boolean delete(long id);
}
