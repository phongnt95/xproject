package com.xproject.service;

import com.xproject.domain.UserX;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing UserX.
 */
public interface UserXService {

    /**
     * Save a userX.
     *
     * @param userX the entity to save
     * @return the persisted entity
     */
    UserX save(UserX userX);

    /**
     * Get all the userXES.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserX> findAll(Pageable pageable);


    /**
     * Get the "id" userX.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserX> findOne(Long id);

    /**
     * Delete the "id" userX.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
