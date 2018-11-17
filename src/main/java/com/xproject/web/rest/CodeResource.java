package com.xproject.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.xproject.domain.Code;
import com.xproject.service.CodeService;
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
 * REST controller for managing Code.
 */
@RestController
@RequestMapping("/api")
public class CodeResource {

    private final Logger log = LoggerFactory.getLogger(CodeResource.class);

    private static final String ENTITY_NAME = "code";

    private final CodeService codeService;

    public CodeResource(CodeService codeService) {
        this.codeService = codeService;
    }

    /**
     * POST  /codes : Create a new code.
     *
     * @param code the code to create
     * @return the ResponseEntity with status 201 (Created) and with body the new code, or with status 400 (Bad Request) if the code has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/codes")
    @Timed
    public ResponseEntity<Code> createCode(@Valid @RequestBody Code code) throws URISyntaxException {
        log.debug("REST request to save Code : {}", code);
        if (code.getId() != null) {
            throw new BadRequestAlertException("A new code cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Code result = codeService.save(code);
        return ResponseEntity.created(new URI("/api/codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /codes : Updates an existing code.
     *
     * @param code the code to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated code,
     * or with status 400 (Bad Request) if the code is not valid,
     * or with status 500 (Internal Server Error) if the code couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/codes")
    @Timed
    public ResponseEntity<Code> updateCode(@Valid @RequestBody Code code) throws URISyntaxException {
        log.debug("REST request to update Code : {}", code);
        if (code.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Code result = codeService.save(code);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, code.getId().toString()))
            .body(result);
    }

    /**
     * GET  /codes : get all the codes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of codes in body
     */
    @GetMapping("/codes")
    @Timed
    public List<Code> getAllCodes() {
        log.debug("REST request to get all Codes");
        return codeService.findAll();
    }

    /**
     * GET  /codes/:id : get the "id" code.
     *
     * @param id the id of the code to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the code, or with status 404 (Not Found)
     */
    @GetMapping("/codes/{id}")
    @Timed
    public ResponseEntity<Code> getCode(@PathVariable Long id) {
        log.debug("REST request to get Code : {}", id);
        Optional<Code> code = codeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(code);
    }

    /**
     * DELETE  /codes/:id : delete the "id" code.
     *
     * @param id the id of the code to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/codes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCode(@PathVariable Long id) {
        log.debug("REST request to delete Code : {}", id);
        codeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
