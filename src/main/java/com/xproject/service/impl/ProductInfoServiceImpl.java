package com.xproject.service.impl;

import com.xproject.service.ProductInfoService;
import com.xproject.domain.ProductInfo;
import com.xproject.repository.ProductInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing ProductInfo.
 */
@Service
@Transactional
public class ProductInfoServiceImpl implements ProductInfoService {

    private final Logger log = LoggerFactory.getLogger(ProductInfoServiceImpl.class);

    private final ProductInfoRepository productInfoRepository;

    public ProductInfoServiceImpl(ProductInfoRepository productInfoRepository) {
        this.productInfoRepository = productInfoRepository;
    }

    /**
     * Save a productInfo.
     *
     * @param productInfo the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductInfo save(ProductInfo productInfo) {
        log.debug("Request to save ProductInfo : {}", productInfo);
        return productInfoRepository.save(productInfo);
    }

    /**
     * Get all the productInfos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductInfo> findAll() {
        log.debug("Request to get all ProductInfos");
        return productInfoRepository.findAll();
    }


    /**
     * Get one productInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductInfo> findOne(Long id) {
        log.debug("Request to get ProductInfo : {}", id);
        return productInfoRepository.findById(id);
    }

    /**
     * Delete the productInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductInfo : {}", id);
        productInfoRepository.deleteById(id);
    }
}
