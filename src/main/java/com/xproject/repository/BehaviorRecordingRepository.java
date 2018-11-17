package com.xproject.repository;

import com.xproject.domain.BehaviorRecording;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BehaviorRecording entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BehaviorRecordingRepository extends JpaRepository<BehaviorRecording, Long> {

}
