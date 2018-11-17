package com.xproject.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.xproject.domain.ServiceOpt;
import com.xproject.service.ServiceOptService;
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
 * REST controller for managing ServiceOpt.
 */
@RestController
@RequestMapping("/api")
public class ServiceOptResource {

    private final Logger log = LoggerFactory.getLogger(ServiceOptResource.class);

    private static final String ENTITY_NAME = "serviceOpt";

    private final ServiceOptService serviceOptService;

    public ServiceOptResource(ServiceOptService serviceOptService) {
        this.serviceOptService = serviceOptService;
    }

    /**
     * POST  /service-opts : Create a new serviceOpt.
     *
     * @param serviceOpt the serviceOpt to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviceOpt, or with status 400 (Bad Request) if the serviceOpt has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/service-opts")
    @Timed
    public ResponseEntity<ServiceOpt> createServiceOpt(@Valid @RequestBody ServiceOpt serviceOpt) throws URISyntaxException {
        log.debug("REST request to save ServiceOpt : {}", serviceOpt);
        if (serviceOpt.getId() != null) {
            throw new BadRequestAlertException("A new serviceOpt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceOpt result = serviceOptService.save(serviceOpt);
        return ResponseEntity.created(new URI("/api/service-opts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /service-opts : Updates an existing serviceOpt.
     *
     * @param serviceOpt the serviceOpt to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceOpt,
     * or with status 400 (Bad Request) if the serviceOpt is not valid,
     * or with status 500 (Internal Server Error) if the serviceOpt couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/service-opts")
    @Timed
    public ResponseEntity<ServiceOpt> updateServiceOpt(@Valid @RequestBody ServiceOpt serviceOpt) throws URISyntaxException {
        log.debug("REST request to update ServiceOpt : {}", serviceOpt);
        if (serviceOpt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceOpt result = serviceOptService.save(serviceOpt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serviceOpt.getId().toString()))
            .body(result);
    }

    /**
     * GET  /service-opts : get all the serviceOpts.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of serviceOpts in body
     */
    @GetMapping("/service-opts")
    @Timed
    public List<ServiceOpt> getAllServiceOpts(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ServiceOpts");
        return serviceOptService.findAll();
    }

    /**
     * GET  /service-opts/:id : get the "id" serviceOpt.
     *
     * @param id the id of the serviceOpt to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviceOpt, or with status 404 (Not Found)
     */
    @GetMapping("/service-opts/{id}")
    @Timed
    public ResponseEntity<ServiceOpt> getServiceOpt(@PathVariable Long id) {
        log.debug("REST request to get ServiceOpt : {}", id);
        Optional<ServiceOpt> serviceOpt = serviceOptService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceOpt);
    }

    /**
     * DELETE  /service-opts/:id : delete the "id" serviceOpt.
     *
     * @param id the id of the serviceOpt to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/service-opts/{id}")
    @Timed
    public ResponseEntity<Void> deleteServiceOpt(@PathVariable Long id) {
        log.debug("REST request to delete ServiceOpt : {}", id);
        serviceOptService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
