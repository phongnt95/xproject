package com.xproject.web.rest;

import com.xproject.XprojectApp;

import com.xproject.domain.ProductInfo;
import com.xproject.repository.ProductInfoRepository;
import com.xproject.service.ProductInfoService;
import com.xproject.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.xproject.web.rest.TestUtil.sameInstant;
import static com.xproject.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductInfoResource REST controller.
 *
 * @see ProductInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XprojectApp.class)
public class ProductInfoResourceIntTest {

    private static final Long DEFAULT_WEIGHT = 1L;
    private static final Long UPDATED_WEIGHT = 2L;

    private static final Long DEFAULT_HIGH = 1L;
    private static final Long UPDATED_HIGH = 2L;

    private static final String DEFAULT_MEASUREMENT_1 = "AAAAAAAAAA";
    private static final String UPDATED_MEASUREMENT_1 = "BBBBBBBBBB";

    private static final String DEFAULT_MEASUREMENT_2 = "AAAAAAAAAA";
    private static final String UPDATED_MEASUREMENT_2 = "BBBBBBBBBB";

    private static final String DEFAULT_MEASUREMENT_3 = "AAAAAAAAAA";
    private static final String UPDATED_MEASUREMENT_3 = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_JOIN_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_JOIN_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_COME_FROM = "AAAAAAAAAA";
    private static final String UPDATED_COME_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_X_INFO = "AAAAAAAAAA";
    private static final String UPDATED_X_INFO = "BBBBBBBBBB";

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductInfoMockMvc;

    private ProductInfo productInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductInfoResource productInfoResource = new ProductInfoResource(productInfoService);
        this.restProductInfoMockMvc = MockMvcBuilders.standaloneSetup(productInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductInfo createEntity(EntityManager em) {
        ProductInfo productInfo = new ProductInfo()
            .weight(DEFAULT_WEIGHT)
            .high(DEFAULT_HIGH)
            .measurement1(DEFAULT_MEASUREMENT_1)
            .measurement2(DEFAULT_MEASUREMENT_2)
            .measurement3(DEFAULT_MEASUREMENT_3)
            .description(DEFAULT_DESCRIPTION)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .joinTime(DEFAULT_JOIN_TIME)
            .comeFrom(DEFAULT_COME_FROM)
            .xInfo(DEFAULT_X_INFO);
        return productInfo;
    }

    @Before
    public void initTest() {
        productInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductInfo() throws Exception {
        int databaseSizeBeforeCreate = productInfoRepository.findAll().size();

        // Create the ProductInfo
        restProductInfoMockMvc.perform(post("/api/product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInfo)))
            .andExpect(status().isCreated());

        // Validate the ProductInfo in the database
        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ProductInfo testProductInfo = productInfoList.get(productInfoList.size() - 1);
        assertThat(testProductInfo.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testProductInfo.getHigh()).isEqualTo(DEFAULT_HIGH);
        assertThat(testProductInfo.getMeasurement1()).isEqualTo(DEFAULT_MEASUREMENT_1);
        assertThat(testProductInfo.getMeasurement2()).isEqualTo(DEFAULT_MEASUREMENT_2);
        assertThat(testProductInfo.getMeasurement3()).isEqualTo(DEFAULT_MEASUREMENT_3);
        assertThat(testProductInfo.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductInfo.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testProductInfo.getJoinTime()).isEqualTo(DEFAULT_JOIN_TIME);
        assertThat(testProductInfo.getComeFrom()).isEqualTo(DEFAULT_COME_FROM);
        assertThat(testProductInfo.getxInfo()).isEqualTo(DEFAULT_X_INFO);
    }

    @Test
    @Transactional
    public void createProductInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productInfoRepository.findAll().size();

        // Create the ProductInfo with an existing ID
        productInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductInfoMockMvc.perform(post("/api/product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ProductInfo in the database
        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = productInfoRepository.findAll().size();
        // set the field null
        productInfo.setDescription(null);

        // Create the ProductInfo, which fails.

        restProductInfoMockMvc.perform(post("/api/product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInfo)))
            .andExpect(status().isBadRequest());

        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = productInfoRepository.findAll().size();
        // set the field null
        productInfo.setPhoneNumber(null);

        // Create the ProductInfo, which fails.

        restProductInfoMockMvc.perform(post("/api/product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInfo)))
            .andExpect(status().isBadRequest());

        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJoinTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productInfoRepository.findAll().size();
        // set the field null
        productInfo.setJoinTime(null);

        // Create the ProductInfo, which fails.

        restProductInfoMockMvc.perform(post("/api/product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInfo)))
            .andExpect(status().isBadRequest());

        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkComeFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = productInfoRepository.findAll().size();
        // set the field null
        productInfo.setComeFrom(null);

        // Create the ProductInfo, which fails.

        restProductInfoMockMvc.perform(post("/api/product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInfo)))
            .andExpect(status().isBadRequest());

        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkxInfoIsRequired() throws Exception {
        int databaseSizeBeforeTest = productInfoRepository.findAll().size();
        // set the field null
        productInfo.setxInfo(null);

        // Create the ProductInfo, which fails.

        restProductInfoMockMvc.perform(post("/api/product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInfo)))
            .andExpect(status().isBadRequest());

        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductInfos() throws Exception {
        // Initialize the database
        productInfoRepository.saveAndFlush(productInfo);

        // Get all the productInfoList
        restProductInfoMockMvc.perform(get("/api/product-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].high").value(hasItem(DEFAULT_HIGH.intValue())))
            .andExpect(jsonPath("$.[*].measurement1").value(hasItem(DEFAULT_MEASUREMENT_1.toString())))
            .andExpect(jsonPath("$.[*].measurement2").value(hasItem(DEFAULT_MEASUREMENT_2.toString())))
            .andExpect(jsonPath("$.[*].measurement3").value(hasItem(DEFAULT_MEASUREMENT_3.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].joinTime").value(hasItem(sameInstant(DEFAULT_JOIN_TIME))))
            .andExpect(jsonPath("$.[*].comeFrom").value(hasItem(DEFAULT_COME_FROM.toString())))
            .andExpect(jsonPath("$.[*].xInfo").value(hasItem(DEFAULT_X_INFO.toString())));
    }
    
    @Test
    @Transactional
    public void getProductInfo() throws Exception {
        // Initialize the database
        productInfoRepository.saveAndFlush(productInfo);

        // Get the productInfo
        restProductInfoMockMvc.perform(get("/api/product-infos/{id}", productInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productInfo.getId().intValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.intValue()))
            .andExpect(jsonPath("$.high").value(DEFAULT_HIGH.intValue()))
            .andExpect(jsonPath("$.measurement1").value(DEFAULT_MEASUREMENT_1.toString()))
            .andExpect(jsonPath("$.measurement2").value(DEFAULT_MEASUREMENT_2.toString()))
            .andExpect(jsonPath("$.measurement3").value(DEFAULT_MEASUREMENT_3.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.joinTime").value(sameInstant(DEFAULT_JOIN_TIME)))
            .andExpect(jsonPath("$.comeFrom").value(DEFAULT_COME_FROM.toString()))
            .andExpect(jsonPath("$.xInfo").value(DEFAULT_X_INFO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductInfo() throws Exception {
        // Get the productInfo
        restProductInfoMockMvc.perform(get("/api/product-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductInfo() throws Exception {
        // Initialize the database
        productInfoService.save(productInfo);

        int databaseSizeBeforeUpdate = productInfoRepository.findAll().size();

        // Update the productInfo
        ProductInfo updatedProductInfo = productInfoRepository.findById(productInfo.getId()).get();
        // Disconnect from session so that the updates on updatedProductInfo are not directly saved in db
        em.detach(updatedProductInfo);
        updatedProductInfo
            .weight(UPDATED_WEIGHT)
            .high(UPDATED_HIGH)
            .measurement1(UPDATED_MEASUREMENT_1)
            .measurement2(UPDATED_MEASUREMENT_2)
            .measurement3(UPDATED_MEASUREMENT_3)
            .description(UPDATED_DESCRIPTION)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .joinTime(UPDATED_JOIN_TIME)
            .comeFrom(UPDATED_COME_FROM)
            .xInfo(UPDATED_X_INFO);

        restProductInfoMockMvc.perform(put("/api/product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductInfo)))
            .andExpect(status().isOk());

        // Validate the ProductInfo in the database
        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeUpdate);
        ProductInfo testProductInfo = productInfoList.get(productInfoList.size() - 1);
        assertThat(testProductInfo.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testProductInfo.getHigh()).isEqualTo(UPDATED_HIGH);
        assertThat(testProductInfo.getMeasurement1()).isEqualTo(UPDATED_MEASUREMENT_1);
        assertThat(testProductInfo.getMeasurement2()).isEqualTo(UPDATED_MEASUREMENT_2);
        assertThat(testProductInfo.getMeasurement3()).isEqualTo(UPDATED_MEASUREMENT_3);
        assertThat(testProductInfo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductInfo.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testProductInfo.getJoinTime()).isEqualTo(UPDATED_JOIN_TIME);
        assertThat(testProductInfo.getComeFrom()).isEqualTo(UPDATED_COME_FROM);
        assertThat(testProductInfo.getxInfo()).isEqualTo(UPDATED_X_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingProductInfo() throws Exception {
        int databaseSizeBeforeUpdate = productInfoRepository.findAll().size();

        // Create the ProductInfo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductInfoMockMvc.perform(put("/api/product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ProductInfo in the database
        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductInfo() throws Exception {
        // Initialize the database
        productInfoService.save(productInfo);

        int databaseSizeBeforeDelete = productInfoRepository.findAll().size();

        // Get the productInfo
        restProductInfoMockMvc.perform(delete("/api/product-infos/{id}", productInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductInfo.class);
        ProductInfo productInfo1 = new ProductInfo();
        productInfo1.setId(1L);
        ProductInfo productInfo2 = new ProductInfo();
        productInfo2.setId(productInfo1.getId());
        assertThat(productInfo1).isEqualTo(productInfo2);
        productInfo2.setId(2L);
        assertThat(productInfo1).isNotEqualTo(productInfo2);
        productInfo1.setId(null);
        assertThat(productInfo1).isNotEqualTo(productInfo2);
    }
}
