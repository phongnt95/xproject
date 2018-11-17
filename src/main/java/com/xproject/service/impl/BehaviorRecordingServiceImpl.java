package com.xproject.service.impl;

import com.xproject.service.BehaviorRecordingService;
import com.xproject.domain.BehaviorRecording;
import com.xproject.repository.BehaviorRecordingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing BehaviorRecording.
 */
@Service
@Transactional
public class BehaviorRecordingServiceImpl implements BehaviorRecordingService {

    private final Logger log = LoggerFactory.getLogger(BehaviorRecordingServiceImpl.class);

    private final BehaviorRecordingRepository behaviorRecordingRepository;

    public BehaviorRecordingServiceImpl(BehaviorRecordingRepository behaviorRecordingRepository) {
        this.behaviorRecordingRepository = behaviorRecordingRepository;
    }

    /**
     * Save a behaviorRecording.
     *
     * @param behaviorRecording the entity to save
     * @return the persisted entity
     */
    @Override
    public BehaviorRecording save(BehaviorRecording behaviorRecording) {
        log.debug("Request to save BehaviorRecording : {}", behaviorRecording);
        return behaviorRecordingRepository.save(behaviorRecording);
    }

    /**
     * Get all the behaviorRecordings.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BehaviorRecording> findAll() {
        log.debug("Request to get all BehaviorRecordings");
        return behaviorRecordingRepository.findAll();
    }


    /**
     * Get one behaviorRecording by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BehaviorRecording> findOne(Long id) {
        log.debug("Request to get BehaviorRecording : {}", id);
        return behaviorRecordingRepository.findById(id);
    }

    /**
     * Delete the behaviorRecording by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BehaviorRecording : {}", id);
        behaviorRecordingRepository.deleteById(id);
    }
}
