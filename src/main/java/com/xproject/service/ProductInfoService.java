package com.xproject.service;

import com.xproject.domain.ProductInfo;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ProductInfo.
 */
public interface ProductInfoService {

    /**
     * Save a productInfo.
     *
     * @param productInfo the entity to save
     * @return the persisted entity
     */
    ProductInfo save(ProductInfo productInfo);

    /**
     * Get all the productInfos.
     *
     * @return the list of entities
     */
    List<ProductInfo> findAll();


    /**
     * Get the "id" productInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProductInfo> findOne(Long id);

    /**
     * Delete the "id" productInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
