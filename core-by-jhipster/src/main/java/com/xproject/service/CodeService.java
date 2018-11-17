package com.xproject.service;

import com.xproject.domain.Code;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Code.
 */
public interface CodeService {

    /**
     * Save a code.
     *
     * @param code the entity to save
     * @return the persisted entity
     */
    Code save(Code code);

    /**
     * Get all the codes.
     *
     * @return the list of entities
     */
    List<Code> findAll();


    /**
     * Get the "id" code.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Code> findOne(Long id);

    /**
     * Delete the "id" code.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
