package com.xproject.service;

import com.xproject.domain.BehaviorRecording;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing BehaviorRecording.
 */
public interface BehaviorRecordingService {

    /**
     * Save a behaviorRecording.
     *
     * @param behaviorRecording the entity to save
     * @return the persisted entity
     */
    BehaviorRecording save(BehaviorRecording behaviorRecording);

    /**
     * Get all the behaviorRecordings.
     *
     * @return the list of entities
     */
    List<BehaviorRecording> findAll();


    /**
     * Get the "id" behaviorRecording.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BehaviorRecording> findOne(Long id);

    /**
     * Delete the "id" behaviorRecording.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
