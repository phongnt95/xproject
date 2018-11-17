package com.xproject.web.rest;

import com.xproject.XprojectApp;

import com.xproject.domain.UserX;
import com.xproject.repository.UserXRepository;
import com.xproject.service.UserXService;
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

import com.xproject.domain.enumeration.XRole;
/**
 * Test class for the UserXResource REST controller.
 *
 * @see UserXResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XprojectApp.class)
public class UserXResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_FULLNAME = "AAAAAAAAAA";
    private static final String UPDATED_FULLNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_X_INFO = "AAAAAAAAAA";
    private static final String UPDATED_X_INFO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final ZonedDateTime DEFAULT_DATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final XRole DEFAULT_ROLE = XRole.CHECKER;
    private static final XRole UPDATED_ROLE = XRole.BROKER;

    @Autowired
    private UserXRepository userXRepository;

    @Autowired
    private UserXService userXService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserXMockMvc;

    private UserX userX;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserXResource userXResource = new UserXResource(userXService);
        this.restUserXMockMvc = MockMvcBuilders.standaloneSetup(userXResource)
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
    public static UserX createEntity(EntityManager em) {
        UserX userX = new UserX()
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .fullname(DEFAULT_FULLNAME)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .xInfo(DEFAULT_X_INFO)
            .status(DEFAULT_STATUS)
            .datetime(DEFAULT_DATETIME)
            .role(DEFAULT_ROLE);
        return userX;
    }

    @Before
    public void initTest() {
        userX = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserX() throws Exception {
        int databaseSizeBeforeCreate = userXRepository.findAll().size();

        // Create the UserX
        restUserXMockMvc.perform(post("/api/user-xes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userX)))
            .andExpect(status().isCreated());

        // Validate the UserX in the database
        List<UserX> userXList = userXRepository.findAll();
        assertThat(userXList).hasSize(databaseSizeBeforeCreate + 1);
        UserX testUserX = userXList.get(userXList.size() - 1);
        assertThat(testUserX.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testUserX.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUserX.getFullname()).isEqualTo(DEFAULT_FULLNAME);
        assertThat(testUserX.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testUserX.getxInfo()).isEqualTo(DEFAULT_X_INFO);
        assertThat(testUserX.isStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserX.getDatetime()).isEqualTo(DEFAULT_DATETIME);
        assertThat(testUserX.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    public void createUserXWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userXRepository.findAll().size();

        // Create the UserX with an existing ID
        userX.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserXMockMvc.perform(post("/api/user-xes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userX)))
            .andExpect(status().isBadRequest());

        // Validate the UserX in the database
        List<UserX> userXList = userXRepository.findAll();
        assertThat(userXList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userXRepository.findAll().size();
        // set the field null
        userX.setUsername(null);

        // Create the UserX, which fails.

        restUserXMockMvc.perform(post("/api/user-xes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userX)))
            .andExpect(status().isBadRequest());

        List<UserX> userXList = userXRepository.findAll();
        assertThat(userXList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = userXRepository.findAll().size();
        // set the field null
        userX.setPassword(null);

        // Create the UserX, which fails.

        restUserXMockMvc.perform(post("/api/user-xes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userX)))
            .andExpect(status().isBadRequest());

        List<UserX> userXList = userXRepository.findAll();
        assertThat(userXList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFullnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userXRepository.findAll().size();
        // set the field null
        userX.setFullname(null);

        // Create the UserX, which fails.

        restUserXMockMvc.perform(post("/api/user-xes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userX)))
            .andExpect(status().isBadRequest());

        List<UserX> userXList = userXRepository.findAll();
        assertThat(userXList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = userXRepository.findAll().size();
        // set the field null
        userX.setPhoneNumber(null);

        // Create the UserX, which fails.

        restUserXMockMvc.perform(post("/api/user-xes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userX)))
            .andExpect(status().isBadRequest());

        List<UserX> userXList = userXRepository.findAll();
        assertThat(userXList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkxInfoIsRequired() throws Exception {
        int databaseSizeBeforeTest = userXRepository.findAll().size();
        // set the field null
        userX.setxInfo(null);

        // Create the UserX, which fails.

        restUserXMockMvc.perform(post("/api/user-xes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userX)))
            .andExpect(status().isBadRequest());

        List<UserX> userXList = userXRepository.findAll();
        assertThat(userXList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = userXRepository.findAll().size();
        // set the field null
        userX.setStatus(null);

        // Create the UserX, which fails.

        restUserXMockMvc.perform(post("/api/user-xes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userX)))
            .andExpect(status().isBadRequest());

        List<UserX> userXList = userXRepository.findAll();
        assertThat(userXList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatetimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userXRepository.findAll().size();
        // set the field null
        userX.setDatetime(null);

        // Create the UserX, which fails.

        restUserXMockMvc.perform(post("/api/user-xes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userX)))
            .andExpect(status().isBadRequest());

        List<UserX> userXList = userXRepository.findAll();
        assertThat(userXList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = userXRepository.findAll().size();
        // set the field null
        userX.setRole(null);

        // Create the UserX, which fails.

        restUserXMockMvc.perform(post("/api/user-xes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userX)))
            .andExpect(status().isBadRequest());

        List<UserX> userXList = userXRepository.findAll();
        assertThat(userXList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserXES() throws Exception {
        // Initialize the database
        userXRepository.saveAndFlush(userX);

        // Get all the userXList
        restUserXMockMvc.perform(get("/api/user-xes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userX.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].fullname").value(hasItem(DEFAULT_FULLNAME.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].xInfo").value(hasItem(DEFAULT_X_INFO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].datetime").value(hasItem(sameInstant(DEFAULT_DATETIME))))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())));
    }
    
    @Test
    @Transactional
    public void getUserX() throws Exception {
        // Initialize the database
        userXRepository.saveAndFlush(userX);

        // Get the userX
        restUserXMockMvc.perform(get("/api/user-xes/{id}", userX.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userX.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.fullname").value(DEFAULT_FULLNAME.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.xInfo").value(DEFAULT_X_INFO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.datetime").value(sameInstant(DEFAULT_DATETIME)))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserX() throws Exception {
        // Get the userX
        restUserXMockMvc.perform(get("/api/user-xes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserX() throws Exception {
        // Initialize the database
        userXService.save(userX);

        int databaseSizeBeforeUpdate = userXRepository.findAll().size();

        // Update the userX
        UserX updatedUserX = userXRepository.findById(userX.getId()).get();
        // Disconnect from session so that the updates on updatedUserX are not directly saved in db
        em.detach(updatedUserX);
        updatedUserX
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .fullname(UPDATED_FULLNAME)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .xInfo(UPDATED_X_INFO)
            .status(UPDATED_STATUS)
            .datetime(UPDATED_DATETIME)
            .role(UPDATED_ROLE);

        restUserXMockMvc.perform(put("/api/user-xes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserX)))
            .andExpect(status().isOk());

        // Validate the UserX in the database
        List<UserX> userXList = userXRepository.findAll();
        assertThat(userXList).hasSize(databaseSizeBeforeUpdate);
        UserX testUserX = userXList.get(userXList.size() - 1);
        assertThat(testUserX.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testUserX.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUserX.getFullname()).isEqualTo(UPDATED_FULLNAME);
        assertThat(testUserX.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testUserX.getxInfo()).isEqualTo(UPDATED_X_INFO);
        assertThat(testUserX.isStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserX.getDatetime()).isEqualTo(UPDATED_DATETIME);
        assertThat(testUserX.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserX() throws Exception {
        int databaseSizeBeforeUpdate = userXRepository.findAll().size();

        // Create the UserX

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserXMockMvc.perform(put("/api/user-xes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userX)))
            .andExpect(status().isBadRequest());

        // Validate the UserX in the database
        List<UserX> userXList = userXRepository.findAll();
        assertThat(userXList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserX() throws Exception {
        // Initialize the database
        userXService.save(userX);

        int databaseSizeBeforeDelete = userXRepository.findAll().size();

        // Get the userX
        restUserXMockMvc.perform(delete("/api/user-xes/{id}", userX.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserX> userXList = userXRepository.findAll();
        assertThat(userXList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserX.class);
        UserX userX1 = new UserX();
        userX1.setId(1L);
        UserX userX2 = new UserX();
        userX2.setId(userX1.getId());
        assertThat(userX1).isEqualTo(userX2);
        userX2.setId(2L);
        assertThat(userX1).isNotEqualTo(userX2);
        userX1.setId(null);
        assertThat(userX1).isNotEqualTo(userX2);
    }
}
