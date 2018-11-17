package com.xproject.web.rest;

import com.xproject.XprojectApp;

import com.xproject.domain.ServiceOpt;
import com.xproject.repository.ServiceOptRepository;
import com.xproject.service.ServiceOptService;
import com.xproject.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.xproject.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ServiceOptResource REST controller.
 *
 * @see ServiceOptResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XprojectApp.class)
public class ServiceOptResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ServiceOptRepository serviceOptRepository;

    @Mock
    private ServiceOptRepository serviceOptRepositoryMock;

    @Mock
    private ServiceOptService serviceOptServiceMock;

    @Autowired
    private ServiceOptService serviceOptService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServiceOptMockMvc;

    private ServiceOpt serviceOpt;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceOptResource serviceOptResource = new ServiceOptResource(serviceOptService);
        this.restServiceOptMockMvc = MockMvcBuilders.standaloneSetup(serviceOptResource)
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
    public static ServiceOpt createEntity(EntityManager em) {
        ServiceOpt serviceOpt = new ServiceOpt()
            .name(DEFAULT_NAME);
        return serviceOpt;
    }

    @Before
    public void initTest() {
        serviceOpt = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceOpt() throws Exception {
        int databaseSizeBeforeCreate = serviceOptRepository.findAll().size();

        // Create the ServiceOpt
        restServiceOptMockMvc.perform(post("/api/service-opts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOpt)))
            .andExpect(status().isCreated());

        // Validate the ServiceOpt in the database
        List<ServiceOpt> serviceOptList = serviceOptRepository.findAll();
        assertThat(serviceOptList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceOpt testServiceOpt = serviceOptList.get(serviceOptList.size() - 1);
        assertThat(testServiceOpt.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createServiceOptWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceOptRepository.findAll().size();

        // Create the ServiceOpt with an existing ID
        serviceOpt.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOptMockMvc.perform(post("/api/service-opts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOpt)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOpt in the database
        List<ServiceOpt> serviceOptList = serviceOptRepository.findAll();
        assertThat(serviceOptList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceOptRepository.findAll().size();
        // set the field null
        serviceOpt.setName(null);

        // Create the ServiceOpt, which fails.

        restServiceOptMockMvc.perform(post("/api/service-opts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOpt)))
            .andExpect(status().isBadRequest());

        List<ServiceOpt> serviceOptList = serviceOptRepository.findAll();
        assertThat(serviceOptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceOpts() throws Exception {
        // Initialize the database
        serviceOptRepository.saveAndFlush(serviceOpt);

        // Get all the serviceOptList
        restServiceOptMockMvc.perform(get("/api/service-opts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOpt.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllServiceOptsWithEagerRelationshipsIsEnabled() throws Exception {
        ServiceOptResource serviceOptResource = new ServiceOptResource(serviceOptServiceMock);
        when(serviceOptServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restServiceOptMockMvc = MockMvcBuilders.standaloneSetup(serviceOptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restServiceOptMockMvc.perform(get("/api/service-opts?eagerload=true"))
        .andExpect(status().isOk());

        verify(serviceOptServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllServiceOptsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ServiceOptResource serviceOptResource = new ServiceOptResource(serviceOptServiceMock);
            when(serviceOptServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restServiceOptMockMvc = MockMvcBuilders.standaloneSetup(serviceOptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restServiceOptMockMvc.perform(get("/api/service-opts?eagerload=true"))
        .andExpect(status().isOk());

            verify(serviceOptServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getServiceOpt() throws Exception {
        // Initialize the database
        serviceOptRepository.saveAndFlush(serviceOpt);

        // Get the serviceOpt
        restServiceOptMockMvc.perform(get("/api/service-opts/{id}", serviceOpt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceOpt.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingServiceOpt() throws Exception {
        // Get the serviceOpt
        restServiceOptMockMvc.perform(get("/api/service-opts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceOpt() throws Exception {
        // Initialize the database
        serviceOptService.save(serviceOpt);

        int databaseSizeBeforeUpdate = serviceOptRepository.findAll().size();

        // Update the serviceOpt
        ServiceOpt updatedServiceOpt = serviceOptRepository.findById(serviceOpt.getId()).get();
        // Disconnect from session so that the updates on updatedServiceOpt are not directly saved in db
        em.detach(updatedServiceOpt);
        updatedServiceOpt
            .name(UPDATED_NAME);

        restServiceOptMockMvc.perform(put("/api/service-opts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceOpt)))
            .andExpect(status().isOk());

        // Validate the ServiceOpt in the database
        List<ServiceOpt> serviceOptList = serviceOptRepository.findAll();
        assertThat(serviceOptList).hasSize(databaseSizeBeforeUpdate);
        ServiceOpt testServiceOpt = serviceOptList.get(serviceOptList.size() - 1);
        assertThat(testServiceOpt.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceOpt() throws Exception {
        int databaseSizeBeforeUpdate = serviceOptRepository.findAll().size();

        // Create the ServiceOpt

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOptMockMvc.perform(put("/api/service-opts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOpt)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOpt in the database
        List<ServiceOpt> serviceOptList = serviceOptRepository.findAll();
        assertThat(serviceOptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceOpt() throws Exception {
        // Initialize the database
        serviceOptService.save(serviceOpt);

        int databaseSizeBeforeDelete = serviceOptRepository.findAll().size();

        // Get the serviceOpt
        restServiceOptMockMvc.perform(delete("/api/service-opts/{id}", serviceOpt.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ServiceOpt> serviceOptList = serviceOptRepository.findAll();
        assertThat(serviceOptList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOpt.class);
        ServiceOpt serviceOpt1 = new ServiceOpt();
        serviceOpt1.setId(1L);
        ServiceOpt serviceOpt2 = new ServiceOpt();
        serviceOpt2.setId(serviceOpt1.getId());
        assertThat(serviceOpt1).isEqualTo(serviceOpt2);
        serviceOpt2.setId(2L);
        assertThat(serviceOpt1).isNotEqualTo(serviceOpt2);
        serviceOpt1.setId(null);
        assertThat(serviceOpt1).isNotEqualTo(serviceOpt2);
    }
}
