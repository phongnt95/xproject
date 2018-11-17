package com.xproject.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.xproject.domain.ProductInfo;
import com.xproject.service.ProductInfoService;
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
 * REST controller for managing ProductInfo.
 */
@RestController
@RequestMapping("/api")
public class ProductInfoResource {

    private final Logger log = LoggerFactory.getLogger(ProductInfoResource.class);

    private static final String ENTITY_NAME = "productInfo";

    private final ProductInfoService productInfoService;

    public ProductInfoResource(ProductInfoService productInfoService) {
        this.productInfoService = productInfoService;
    }

    /**
     * POST  /product-infos : Create a new productInfo.
     *
     * @param productInfo the productInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productInfo, or with status 400 (Bad Request) if the productInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-infos")
    @Timed
    public ResponseEntity<ProductInfo> createProductInfo(@Valid @RequestBody ProductInfo productInfo) throws URISyntaxException {
        log.debug("REST request to save ProductInfo : {}", productInfo);
        if (productInfo.getId() != null) {
            throw new BadRequestAlertException("A new productInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductInfo result = productInfoService.save(productInfo);
        return ResponseEntity.created(new URI("/api/product-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-infos : Updates an existing productInfo.
     *
     * @param productInfo the productInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productInfo,
     * or with status 400 (Bad Request) if the productInfo is not valid,
     * or with status 500 (Internal Server Error) if the productInfo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-infos")
    @Timed
    public ResponseEntity<ProductInfo> updateProductInfo(@Valid @RequestBody ProductInfo productInfo) throws URISyntaxException {
        log.debug("REST request to update ProductInfo : {}", productInfo);
        if (productInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductInfo result = productInfoService.save(productInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-infos : get all the productInfos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productInfos in body
     */
    @GetMapping("/product-infos")
    @Timed
    public List<ProductInfo> getAllProductInfos() {
        log.debug("REST request to get all ProductInfos");
        return productInfoService.findAll();
    }

    /**
     * GET  /product-infos/:id : get the "id" productInfo.
     *
     * @param id the id of the productInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productInfo, or with status 404 (Not Found)
     */
    @GetMapping("/product-infos/{id}")
    @Timed
    public ResponseEntity<ProductInfo> getProductInfo(@PathVariable Long id) {
        log.debug("REST request to get ProductInfo : {}", id);
        Optional<ProductInfo> productInfo = productInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productInfo);
    }

    /**
     * DELETE  /product-infos/:id : delete the "id" productInfo.
     *
     * @param id the id of the productInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductInfo(@PathVariable Long id) {
        log.debug("REST request to delete ProductInfo : {}", id);
        productInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
