package com.xproject.service.impl;

import com.xproject.service.ServiceOptService;
import com.xproject.domain.ServiceOpt;
import com.xproject.repository.ServiceOptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing ServiceOpt.
 */
@Service
@Transactional
public class ServiceOptServiceImpl implements ServiceOptService {

    private final Logger log = LoggerFactory.getLogger(ServiceOptServiceImpl.class);

    private final ServiceOptRepository serviceOptRepository;

    public ServiceOptServiceImpl(ServiceOptRepository serviceOptRepository) {
        this.serviceOptRepository = serviceOptRepository;
    }

    /**
     * Save a serviceOpt.
     *
     * @param serviceOpt the entity to save
     * @return the persisted entity
     */
    @Override
    public ServiceOpt save(ServiceOpt serviceOpt) {
        log.debug("Request to save ServiceOpt : {}", serviceOpt);
        return serviceOptRepository.save(serviceOpt);
    }

    /**
     * Get all the serviceOpts.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ServiceOpt> findAll() {
        log.debug("Request to get all ServiceOpts");
        return serviceOptRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the ServiceOpt with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ServiceOpt> findAllWithEagerRelationships(Pageable pageable) {
        return serviceOptRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one serviceOpt by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceOpt> findOne(Long id) {
        log.debug("Request to get ServiceOpt : {}", id);
        return serviceOptRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the serviceOpt by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceOpt : {}", id);
        serviceOptRepository.deleteById(id);
    }
}
