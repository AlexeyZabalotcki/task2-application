package com.specific.group.service;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.specific.group.dao.Attributes;
import com.specific.group.dto.CreateDto;
import com.specific.group.dto.UpdateDto;

import java.util.List;
import java.util.Map;

/**
 * Provides methode for processing input data from controller and send them to dao
 */
public interface Service {

    /**
     * Validation input data and create request to dao
     *
     * @param attributes a set of request attributes
     * @return a list of json object
     */
    List<JsonObject> search(Map<Attributes, String> attributes);

    /**
     * Validation dto data and send request to dao
     *
     * @param dto a request from user
     * @return a long provides id element in element table
     */
    long create(CreateDto dto);

    /**
     * Create request to dao and validation dto request
     *
     * @param dto a request from user
     * @return a long provides id element in element table
     */
    long update(UpdateDto dto);

    /**
     * Methode validation and send data to dao
     *
     * @param id a string
     * @return a boolean result
     */
    boolean deleteById(String id);
}
