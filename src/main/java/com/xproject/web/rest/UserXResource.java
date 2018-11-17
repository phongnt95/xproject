package com.xproject.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.xproject.domain.UserX;
import com.xproject.service.UserXService;
import com.xproject.web.rest.errors.BadRequestAlertException;
import com.xproject.web.rest.util.HeaderUtil;
import com.xproject.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserX.
 */
@RestController
@RequestMapping("/api")
public class UserXResource {

    private final Logger log = LoggerFactory.getLogger(UserXResource.class);

    private static final String ENTITY_NAME = "userX";

    private final UserXService userXService;

    public UserXResource(UserXService userXService) {
        this.userXService = userXService;
    }

    /**
     * POST  /user-xes : Create a new userX.
     *
     * @param userX the userX to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userX, or with status 400 (Bad Request) if the userX has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-xes")
    @Timed
    public ResponseEntity<UserX> createUserX(@Valid @RequestBody UserX userX) throws URISyntaxException {
        log.debug("REST request to save UserX : {}", userX);
        if (userX.getId() != null) {
            throw new BadRequestAlertException("A new userX cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserX result = userXService.save(userX);
        return ResponseEntity.created(new URI("/api/user-xes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-xes : Updates an existing userX.
     *
     * @param userX the userX to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userX,
     * or with status 400 (Bad Request) if the userX is not valid,
     * or with status 500 (Internal Server Error) if the userX couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-xes")
    @Timed
    public ResponseEntity<UserX> updateUserX(@Valid @RequestBody UserX userX) throws URISyntaxException {
        log.debug("REST request to update UserX : {}", userX);
        if (userX.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserX result = userXService.save(userX);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userX.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-xes : get all the userXES.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userXES in body
     */
    @GetMapping("/user-xes")
    @Timed
    public ResponseEntity<List<UserX>> getAllUserXES(Pageable pageable) {
        log.debug("REST request to get a page of UserXES");
        Page<UserX> page = userXService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-xes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /user-xes/:id : get the "id" userX.
     *
     * @param id the id of the userX to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userX, or with status 404 (Not Found)
     */
    @GetMapping("/user-xes/{id}")
    @Timed
    public ResponseEntity<UserX> getUserX(@PathVariable Long id) {
        log.debug("REST request to get UserX : {}", id);
        Optional<UserX> userX = userXService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userX);
    }

    /**
     * DELETE  /user-xes/:id : delete the "id" userX.
     *
     * @param id the id of the userX to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-xes/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserX(@PathVariable Long id) {
        log.debug("REST request to delete UserX : {}", id);
        userXService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
