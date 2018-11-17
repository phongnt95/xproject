package com.xproject.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.xproject.domain.BehaviorRecording;
import com.xproject.service.BehaviorRecordingService;
import com.xproject.web.rest.errors.BadRequestAlertException;
import com.xproject.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BehaviorRecording.
 */
@RestController
@RequestMapping("/api")
public class BehaviorRecordingResource {

    private final Logger log = LoggerFactory.getLogger(BehaviorRecordingResource.class);

    private static final String ENTITY_NAME = "behaviorRecording";

    private final BehaviorRecordingService behaviorRecordingService;

    public BehaviorRecordingResource(BehaviorRecordingService behaviorRecordingService) {
        this.behaviorRecordingService = behaviorRecordingService;
    }

    /**
     * POST  /behavior-recordings : Create a new behaviorRecording.
     *
     * @param behaviorRecording the behaviorRecording to create
     * @return the ResponseEntity with status 201 (Created) and with body the new behaviorRecording, or with status 400 (Bad Request) if the behaviorRecording has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/behavior-recordings")
    @Timed
    public ResponseEntity<BehaviorRecording> createBehaviorRecording(@Valid @RequestBody BehaviorRecording behaviorRecording) throws URISyntaxException {
        log.debug("REST request to save BehaviorRecording : {}", behaviorRecording);
        if (behaviorRecording.getId() != null) {
            throw new BadRequestAlertException("A new behaviorRecording cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BehaviorRecording result = behaviorRecordingService.save(behaviorRecording);
        return ResponseEntity.created(new URI("/api/behavior-recordings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /behavior-recordings : Updates an existing behaviorRecording.
     *
     * @param behaviorRecording the behaviorRecording to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated behaviorRecording,
     * or with status 400 (Bad Request) if the behaviorRecording is not valid,
     * or with status 500 (Internal Server Error) if the behaviorRecording couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/behavior-recordings")
    @Timed
    public ResponseEntity<BehaviorRecording> updateBehaviorRecording(@Valid @RequestBody BehaviorRecording behaviorRecording) throws URISyntaxException {
        log.debug("REST request to update BehaviorRecording : {}", behaviorRecording);
        if (behaviorRecording.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BehaviorRecording result = behaviorRecordingService.save(behaviorRecording);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, behaviorRecording.getId().toString()))
            .body(result);
    }

    /**
     * GET  /behavior-recordings : get all the behaviorRecordings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of behaviorRecordings in body
     */
    @GetMapping("/behavior-recordings")
    @Timed
    public List<BehaviorRecording> getAllBehaviorRecordings() {
        log.debug("REST request to get all BehaviorRecordings");
        return behaviorRecordingService.findAll();
    }

    /**
     * GET  /behavior-recordings/:id : get the "id" behaviorRecording.
     *
     * @param id the id of the behaviorRecording to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the behaviorRecording, or with status 404 (Not Found)
     */
    @GetMapping("/behavior-recordings/{id}")
    @Timed
    public ResponseEntity<BehaviorRecording> getBehaviorRecording(@PathVariable Long id) {
        log.debug("REST request to get BehaviorRecording : {}", id);
        Optional<BehaviorRecording> behaviorRecording = behaviorRecordingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(behaviorRecording);
    }

    /**
     * DELETE  /behavior-recordings/:id : delete the "id" behaviorRecording.
     *
     * @param id the id of the behaviorRecording to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/behavior-recordings/{id}")
    @Timed
    public ResponseEntity<Void> deleteBehaviorRecording(@PathVariable Long id) {
        log.debug("REST request to delete BehaviorRecording : {}", id);
        behaviorRecordingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
