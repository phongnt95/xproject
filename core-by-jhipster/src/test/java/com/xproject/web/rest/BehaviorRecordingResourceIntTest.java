package com.xproject.web.rest;

import com.xproject.XprojectApp;

import com.xproject.domain.BehaviorRecording;
import com.xproject.repository.BehaviorRecordingRepository;
import com.xproject.service.BehaviorRecordingService;
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

import com.xproject.domain.enumeration.Action;
/**
 * Test class for the BehaviorRecordingResource REST controller.
 *
 * @see BehaviorRecordingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XprojectApp.class)
public class BehaviorRecordingResourceIntTest {

    private static final Action DEFAULT_ACTION = Action.VIEW;
    private static final Action UPDATED_ACTION = Action.GETCODE;

    private static final ZonedDateTime DEFAULT_DATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BehaviorRecordingRepository behaviorRecordingRepository;

    @Autowired
    private BehaviorRecordingService behaviorRecordingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBehaviorRecordingMockMvc;

    private BehaviorRecording behaviorRecording;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BehaviorRecordingResource behaviorRecordingResource = new BehaviorRecordingResource(behaviorRecordingService);
        this.restBehaviorRecordingMockMvc = MockMvcBuilders.standaloneSetup(behaviorRecordingResource)
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
    public static BehaviorRecording createEntity(EntityManager em) {
        BehaviorRecording behaviorRecording = new BehaviorRecording()
            .action(DEFAULT_ACTION)
            .datetime(DEFAULT_DATETIME);
        return behaviorRecording;
    }

    @Before
    public void initTest() {
        behaviorRecording = createEntity(em);
    }

    @Test
    @Transactional
    public void createBehaviorRecording() throws Exception {
        int databaseSizeBeforeCreate = behaviorRecordingRepository.findAll().size();

        // Create the BehaviorRecording
        restBehaviorRecordingMockMvc.perform(post("/api/behavior-recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(behaviorRecording)))
            .andExpect(status().isCreated());

        // Validate the BehaviorRecording in the database
        List<BehaviorRecording> behaviorRecordingList = behaviorRecordingRepository.findAll();
        assertThat(behaviorRecordingList).hasSize(databaseSizeBeforeCreate + 1);
        BehaviorRecording testBehaviorRecording = behaviorRecordingList.get(behaviorRecordingList.size() - 1);
        assertThat(testBehaviorRecording.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testBehaviorRecording.getDatetime()).isEqualTo(DEFAULT_DATETIME);
    }

    @Test
    @Transactional
    public void createBehaviorRecordingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = behaviorRecordingRepository.findAll().size();

        // Create the BehaviorRecording with an existing ID
        behaviorRecording.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBehaviorRecordingMockMvc.perform(post("/api/behavior-recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(behaviorRecording)))
            .andExpect(status().isBadRequest());

        // Validate the BehaviorRecording in the database
        List<BehaviorRecording> behaviorRecordingList = behaviorRecordingRepository.findAll();
        assertThat(behaviorRecordingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = behaviorRecordingRepository.findAll().size();
        // set the field null
        behaviorRecording.setAction(null);

        // Create the BehaviorRecording, which fails.

        restBehaviorRecordingMockMvc.perform(post("/api/behavior-recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(behaviorRecording)))
            .andExpect(status().isBadRequest());

        List<BehaviorRecording> behaviorRecordingList = behaviorRecordingRepository.findAll();
        assertThat(behaviorRecordingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatetimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = behaviorRecordingRepository.findAll().size();
        // set the field null
        behaviorRecording.setDatetime(null);

        // Create the BehaviorRecording, which fails.

        restBehaviorRecordingMockMvc.perform(post("/api/behavior-recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(behaviorRecording)))
            .andExpect(status().isBadRequest());

        List<BehaviorRecording> behaviorRecordingList = behaviorRecordingRepository.findAll();
        assertThat(behaviorRecordingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBehaviorRecordings() throws Exception {
        // Initialize the database
        behaviorRecordingRepository.saveAndFlush(behaviorRecording);

        // Get all the behaviorRecordingList
        restBehaviorRecordingMockMvc.perform(get("/api/behavior-recordings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(behaviorRecording.getId().intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())))
            .andExpect(jsonPath("$.[*].datetime").value(hasItem(sameInstant(DEFAULT_DATETIME))));
    }
    
    @Test
    @Transactional
    public void getBehaviorRecording() throws Exception {
        // Initialize the database
        behaviorRecordingRepository.saveAndFlush(behaviorRecording);

        // Get the behaviorRecording
        restBehaviorRecordingMockMvc.perform(get("/api/behavior-recordings/{id}", behaviorRecording.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(behaviorRecording.getId().intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION.toString()))
            .andExpect(jsonPath("$.datetime").value(sameInstant(DEFAULT_DATETIME)));
    }

    @Test
    @Transactional
    public void getNonExistingBehaviorRecording() throws Exception {
        // Get the behaviorRecording
        restBehaviorRecordingMockMvc.perform(get("/api/behavior-recordings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBehaviorRecording() throws Exception {
        // Initialize the database
        behaviorRecordingService.save(behaviorRecording);

        int databaseSizeBeforeUpdate = behaviorRecordingRepository.findAll().size();

        // Update the behaviorRecording
        BehaviorRecording updatedBehaviorRecording = behaviorRecordingRepository.findById(behaviorRecording.getId()).get();
        // Disconnect from session so that the updates on updatedBehaviorRecording are not directly saved in db
        em.detach(updatedBehaviorRecording);
        updatedBehaviorRecording
            .action(UPDATED_ACTION)
            .datetime(UPDATED_DATETIME);

        restBehaviorRecordingMockMvc.perform(put("/api/behavior-recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBehaviorRecording)))
            .andExpect(status().isOk());

        // Validate the BehaviorRecording in the database
        List<BehaviorRecording> behaviorRecordingList = behaviorRecordingRepository.findAll();
        assertThat(behaviorRecordingList).hasSize(databaseSizeBeforeUpdate);
        BehaviorRecording testBehaviorRecording = behaviorRecordingList.get(behaviorRecordingList.size() - 1);
        assertThat(testBehaviorRecording.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testBehaviorRecording.getDatetime()).isEqualTo(UPDATED_DATETIME);
    }

    @Test
    @Transactional
    public void updateNonExistingBehaviorRecording() throws Exception {
        int databaseSizeBeforeUpdate = behaviorRecordingRepository.findAll().size();

        // Create the BehaviorRecording

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBehaviorRecordingMockMvc.perform(put("/api/behavior-recordings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(behaviorRecording)))
            .andExpect(status().isBadRequest());

        // Validate the BehaviorRecording in the database
        List<BehaviorRecording> behaviorRecordingList = behaviorRecordingRepository.findAll();
        assertThat(behaviorRecordingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBehaviorRecording() throws Exception {
        // Initialize the database
        behaviorRecordingService.save(behaviorRecording);

        int databaseSizeBeforeDelete = behaviorRecordingRepository.findAll().size();

        // Get the behaviorRecording
        restBehaviorRecordingMockMvc.perform(delete("/api/behavior-recordings/{id}", behaviorRecording.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BehaviorRecording> behaviorRecordingList = behaviorRecordingRepository.findAll();
        assertThat(behaviorRecordingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BehaviorRecording.class);
        BehaviorRecording behaviorRecording1 = new BehaviorRecording();
        behaviorRecording1.setId(1L);
        BehaviorRecording behaviorRecording2 = new BehaviorRecording();
        behaviorRecording2.setId(behaviorRecording1.getId());
        assertThat(behaviorRecording1).isEqualTo(behaviorRecording2);
        behaviorRecording2.setId(2L);
        assertThat(behaviorRecording1).isNotEqualTo(behaviorRecording2);
        behaviorRecording1.setId(null);
        assertThat(behaviorRecording1).isNotEqualTo(behaviorRecording2);
    }
}
