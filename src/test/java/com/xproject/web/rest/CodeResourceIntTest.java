package com.xproject.web.rest;

import com.xproject.XprojectApp;

import com.xproject.domain.Code;
import com.xproject.repository.CodeRepository;
import com.xproject.service.CodeService;
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
import java.util.List;


import static com.xproject.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CodeResource REST controller.
 *
 * @see CodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XprojectApp.class)
public class CodeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private CodeService codeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCodeMockMvc;

    private Code code;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CodeResource codeResource = new CodeResource(codeService);
        this.restCodeMockMvc = MockMvcBuilders.standaloneSetup(codeResource)
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
    public static Code createEntity(EntityManager em) {
        Code code = new Code()
            .code(DEFAULT_CODE);
        return code;
    }

    @Before
    public void initTest() {
        code = createEntity(em);
    }

    @Test
    @Transactional
    public void createCode() throws Exception {
        int databaseSizeBeforeCreate = codeRepository.findAll().size();

        // Create the Code
        restCodeMockMvc.perform(post("/api/codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(code)))
            .andExpect(status().isCreated());

        // Validate the Code in the database
        List<Code> codeList = codeRepository.findAll();
        assertThat(codeList).hasSize(databaseSizeBeforeCreate + 1);
        Code testCode = codeList.get(codeList.size() - 1);
        assertThat(testCode.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createCodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = codeRepository.findAll().size();

        // Create the Code with an existing ID
        code.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCodeMockMvc.perform(post("/api/codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(code)))
            .andExpect(status().isBadRequest());

        // Validate the Code in the database
        List<Code> codeList = codeRepository.findAll();
        assertThat(codeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = codeRepository.findAll().size();
        // set the field null
        code.setCode(null);

        // Create the Code, which fails.

        restCodeMockMvc.perform(post("/api/codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(code)))
            .andExpect(status().isBadRequest());

        List<Code> codeList = codeRepository.findAll();
        assertThat(codeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCodes() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get all the codeList
        restCodeMockMvc.perform(get("/api/codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(code.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getCode() throws Exception {
        // Initialize the database
        codeRepository.saveAndFlush(code);

        // Get the code
        restCodeMockMvc.perform(get("/api/codes/{id}", code.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(code.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCode() throws Exception {
        // Get the code
        restCodeMockMvc.perform(get("/api/codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCode() throws Exception {
        // Initialize the database
        codeService.save(code);

        int databaseSizeBeforeUpdate = codeRepository.findAll().size();

        // Update the code
        Code updatedCode = codeRepository.findById(code.getId()).get();
        // Disconnect from session so that the updates on updatedCode are not directly saved in db
        em.detach(updatedCode);
        updatedCode
            .code(UPDATED_CODE);

        restCodeMockMvc.perform(put("/api/codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCode)))
            .andExpect(status().isOk());

        // Validate the Code in the database
        List<Code> codeList = codeRepository.findAll();
        assertThat(codeList).hasSize(databaseSizeBeforeUpdate);
        Code testCode = codeList.get(codeList.size() - 1);
        assertThat(testCode.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingCode() throws Exception {
        int databaseSizeBeforeUpdate = codeRepository.findAll().size();

        // Create the Code

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodeMockMvc.perform(put("/api/codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(code)))
            .andExpect(status().isBadRequest());

        // Validate the Code in the database
        List<Code> codeList = codeRepository.findAll();
        assertThat(codeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCode() throws Exception {
        // Initialize the database
        codeService.save(code);

        int databaseSizeBeforeDelete = codeRepository.findAll().size();

        // Get the code
        restCodeMockMvc.perform(delete("/api/codes/{id}", code.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Code> codeList = codeRepository.findAll();
        assertThat(codeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Code.class);
        Code code1 = new Code();
        code1.setId(1L);
        Code code2 = new Code();
        code2.setId(code1.getId());
        assertThat(code1).isEqualTo(code2);
        code2.setId(2L);
        assertThat(code1).isNotEqualTo(code2);
        code1.setId(null);
        assertThat(code1).isNotEqualTo(code2);
    }
}
