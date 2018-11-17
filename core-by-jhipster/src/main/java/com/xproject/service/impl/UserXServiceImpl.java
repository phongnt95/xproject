package com.xproject.service.impl;

import com.xproject.service.UserXService;
import com.xproject.domain.UserX;
import com.xproject.repository.UserXRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing UserX.
 */
@Service
@Transactional
public class UserXServiceImpl implements UserXService {

    private final Logger log = LoggerFactory.getLogger(UserXServiceImpl.class);

    private final UserXRepository userXRepository;

    public UserXServiceImpl(UserXRepository userXRepository) {
        this.userXRepository = userXRepository;
    }

    /**
     * Save a userX.
     *
     * @param userX the entity to save
     * @return the persisted entity
     */
    @Override
    public UserX save(UserX userX) {
        log.debug("Request to save UserX : {}", userX);
        return userXRepository.save(userX);
    }

    /**
     * Get all the userXES.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserX> findAll(Pageable pageable) {
        log.debug("Request to get all UserXES");
        return userXRepository.findAll(pageable);
    }


    /**
     * Get one userX by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserX> findOne(Long id) {
        log.debug("Request to get UserX : {}", id);
        return userXRepository.findById(id);
    }

    /**
     * Delete the userX by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserX : {}", id);
        userXRepository.deleteById(id);
    }
}
