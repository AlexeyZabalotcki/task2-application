package com.specific.group.controller;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.specific.group.dao.Attributes;
import com.specific.group.dto.CreateDto;
import com.specific.group.dto.UpdateDto;
import com.specific.group.service.Service;
import com.sun.net.httpserver.HttpExchange;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.specific.group.dao.Attributes.DEPARTMENT_ID;
import static com.specific.group.dao.Attributes.FIRST_NAME;
import static com.specific.group.dao.Attributes.ID;
import static com.specific.group.dao.Attributes.LAST_NAME;
import static com.specific.group.dao.Attributes.POSITION_ID;

/**
 * {@inheritDoc}
 */
public class EmployeeController implements Controller{

    private final Service service;

    public EmployeeController(Service service) {
        this.service = service;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean delete(HttpExchange httpExchange) {
        URI uri = httpExchange.getRequestURI();
        Optional<String> id = readAttributes(uri, ID);
        boolean result = false;
        if (id.isPresent()) {
            result = service.deleteById(id.get());
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<JsonObject> search(HttpExchange httpExchange) {
        URI requestURI = httpExchange.getRequestURI();
        Map<Attributes, String> attributes = new HashMap<>();
        searchAttributeUrl(requestURI, attributes);
        return service.search(attributes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long create(HttpExchange httpExchange) {
        JsonObject jsonObject = readRequestFromJson(httpExchange);
        String firstName = jsonObject.getString(FIRST_NAME);
        String lastName = jsonObject.getString(LAST_NAME);
        String depId = jsonObject.getString(DEPARTMENT_ID);
        String posId = jsonObject.getString(POSITION_ID);
        CreateDto dto = new CreateDto(firstName, lastName, depId, posId);
        return service.create(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long update(HttpExchange httpExchange) {
        JsonObject jsonObject = readRequestFromJson(httpExchange);
        String id = jsonObject.getString(ID);
        String firstName = jsonObject.getString(FIRST_NAME);
        String lastName = jsonObject.getString(LAST_NAME);
        String depId = jsonObject.getString(DEPARTMENT_ID);
        String posId = jsonObject.getString(POSITION_ID);
        UpdateDto dto = new UpdateDto(id, firstName, lastName, depId, posId);
        return service.update(dto);
    }
}
