package com.xproject.service.impl;

import com.xproject.service.CodeService;
import com.xproject.domain.Code;
import com.xproject.repository.CodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Code.
 */
@Service
@Transactional
public class CodeServiceImpl implements CodeService {

    private final Logger log = LoggerFactory.getLogger(CodeServiceImpl.class);

    private final CodeRepository codeRepository;

    public CodeServiceImpl(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    /**
     * Save a code.
     *
     * @param code the entity to save
     * @return the persisted entity
     */
    @Override
    public Code save(Code code) {
        log.debug("Request to save Code : {}", code);
        return codeRepository.save(code);
    }

    /**
     * Get all the codes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Code> findAll() {
        log.debug("Request to get all Codes");
        return codeRepository.findAll();
    }


    /**
     * Get one code by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Code> findOne(Long id) {
        log.debug("Request to get Code : {}", id);
        return codeRepository.findById(id);
    }

    /**
     * Delete the code by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Code : {}", id);
        codeRepository.deleteById(id);
    }
}
