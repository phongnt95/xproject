package com.xproject.service;

import com.xproject.domain.ServiceOpt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ServiceOpt.
 */
public interface ServiceOptService {

    /**
     * Save a serviceOpt.
     *
     * @param serviceOpt the entity to save
     * @return the persisted entity
     */
    ServiceOpt save(ServiceOpt serviceOpt);

    /**
     * Get all the serviceOpts.
     *
     * @return the list of entities
     */
    List<ServiceOpt> findAll();

    /**
     * Get all the ServiceOpt with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<ServiceOpt> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" serviceOpt.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ServiceOpt> findOne(Long id);

    /**
     * Delete the "id" serviceOpt.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
