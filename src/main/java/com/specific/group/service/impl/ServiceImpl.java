package com.specific.group.service.impl;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.specific.group.dao.Attributes;
import com.specific.group.dao.EmployeeDao;
import com.specific.group.dto.CreateDto;
import com.specific.group.dto.UpdateDto;
import com.specific.group.entity.Employee;
import com.specific.group.service.RegularExpression;
import com.specific.group.service.Service;
import com.specific.group.utils.validator.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.specific.group.constants.Constants.Messages.*;
import static com.specific.group.dao.Attributes.DEPARTMENT_ID;
import static com.specific.group.dao.Attributes.DEPARTMENT_NAME;
import static com.specific.group.dao.Attributes.FIRST_NAME;
import static com.specific.group.dao.Attributes.ID;
import static com.specific.group.dao.Attributes.LAST_NAME;
import static com.specific.group.dao.Attributes.POSITION_ID;
import static com.specific.group.dao.Attributes.POSITION_NAME;
import static com.specific.group.dao.Sql.Constant.INVALID_RESULT;
import static com.specific.group.service.RegularExpression.REG_DIGIT;
import static com.specific.group.service.RegularExpression.REG_NAME;

/**
 * {@inheritDoc}
 */
public class ServiceImpl implements Service {

    private final EmployeeDao employeeDao;

    public ServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteById(String id) {
        boolean result = false;
        if (id != null) {
            try {
                Validator<String> validatorId = Validator.of(id).validator(i -> i.matches(REG_DIGIT), WRONG_ID);
                result = validatorId.isEmpty() && employeeDao.delete(Long.parseLong(validatorId.get()));
            } catch (NullPointerException e) {
                return false;
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long create(CreateDto dto) {
        long result = INVALID_RESULT;
        if (dto != null) {
            try {
                Validator<CreateDto> validator = Validator.of(dto)
                        .validator(c -> c.firstName().matches(REG_NAME), WRONG_NAME)
                        .validator(c -> c.lastName().matches(REG_NAME), WRONG_NAME)
                        .validator(c -> c.department().matches(REG_DIGIT), WRONG_DEPARTMENT)
                        .validator(c -> c.position().matches(REG_DIGIT), WRONG_POSITION);
                result = validator.isEmpty() ? employeeDao.create(dto) : result;
            } catch (NullPointerException e) {
                return result;
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long update(UpdateDto dto) {
        long result = INVALID_RESULT;
        if (dto != null) {
            try {
                Validator<UpdateDto> validator = Validator.of(dto)
                        .validator(c -> c.id().matches(REG_DIGIT), WRONG_ID)
                        .validator(c -> c.firstName().matches(REG_NAME), WRONG_NAME)
                        .validator(c -> c.lastName().matches(REG_NAME), WRONG_NAME)
                        .validator(c -> c.department().matches(REG_DIGIT), WRONG_DEPARTMENT)
                        .validator(c -> c.position().matches(REG_DIGIT), WRONG_POSITION);
                result = validator.isEmpty() ? employeeDao.update(dto) : result;
            } catch (NullPointerException e) {
                return result;
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<JsonObject> search(Map<Attributes, String> attributes) {
        boolean validation;
        List<Employee> result = new ArrayList<>();
        List<JsonObject> jsonObjects = new ArrayList<>();
        if (attributes != null) {
            Validator<Map<Attributes, String>> validator = Validator.of(attributes);
            try {
                attributes.forEach((k, v) -> {
                    switch (k) {
                        case ID -> validator.validator(var -> var.get(ID).matches(REG_DIGIT), WRONG_ID);
                        case FIRST_NAME ->
                                validator.validator(var -> var.get(FIRST_NAME).matches(RegularExpression.REG_NAME), WRONG_NAME);
                        case LAST_NAME ->
                                validator.validator(var -> var.get(LAST_NAME).matches(RegularExpression.REG_NAME), WRONG_NAME);
                        case DEPARTMENT_ID ->
                                validator.validator(var -> var.get(DEPARTMENT_ID).matches(REG_DIGIT), WRONG_DEPARTMENT);
                        case POSITION_ID ->
                                validator.validator(var -> var.get(POSITION_ID).matches(REG_DIGIT), WRONG_DEPARTMENT);
                    }
                });
                validation = validator.isEmpty();
            } catch (NullPointerException e) {
                validation = false;
            }
            result = validation ? employeeDao.search(attributes) : result;
        }
        for (Employee search : result) {
            JsonObject json = new JsonObject();
            json.put(ID, search.getId());
            json.put(FIRST_NAME, search.getFirstName());
            json.put(LAST_NAME, search.getLastName());
            json.put(DEPARTMENT_ID, search.getDepartment().getId());
            json.put(DEPARTMENT_NAME, search.getDepartment().getName());
            json.put(POSITION_ID, search.getPosition().getId());
            json.put(POSITION_NAME, search.getPosition().getName());
            jsonObjects.add(json);
        }
        return jsonObjects;
    }
}
