package io.github.leassets.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.leassets.IntegrationTest;
import io.github.leassets.domain.LeassetsFileType;
import io.github.leassets.domain.enumeration.LeassetsFileMediumTypes;
import io.github.leassets.domain.enumeration.LeassetsFileModelType;
import io.github.leassets.repository.LeassetsFileTypeRepository;
import io.github.leassets.repository.search.LeassetsFileTypeSearchRepository;
import io.github.leassets.service.criteria.LeassetsFileTypeCriteria;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link LeassetsFileTypeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LeassetsFileTypeResourceIT {

    private static final String DEFAULT_LEASSETS_FILE_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LEASSETS_FILE_TYPE_NAME = "BBBBBBBBBB";

    private static final LeassetsFileMediumTypes DEFAULT_LEASSETS_FILE_MEDIUM_TYPE = LeassetsFileMediumTypes.EXCEL;
    private static final LeassetsFileMediumTypes UPDATED_LEASSETS_FILE_MEDIUM_TYPE = LeassetsFileMediumTypes.EXCEL_XLS;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FILE_TEMPLATE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE_TEMPLATE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_TEMPLATE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_TEMPLATE_CONTENT_TYPE = "image/png";

    private static final LeassetsFileModelType DEFAULT_LEASSETSFILE_TYPE = LeassetsFileModelType.CURRENCY_LIST;
    private static final LeassetsFileModelType UPDATED_LEASSETSFILE_TYPE = LeassetsFileModelType.FIXED_ASSET_ACQUISITION;

    private static final String ENTITY_API_URL = "/api/leassets-file-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/leassets-file-types";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeassetsFileTypeRepository leassetsFileTypeRepository;

    /**
     * This repository is mocked in the io.github.leassets.repository.search test package.
     *
     * @see io.github.leassets.repository.search.LeassetsFileTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private LeassetsFileTypeSearchRepository mockLeassetsFileTypeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeassetsFileTypeMockMvc;

    private LeassetsFileType leassetsFileType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeassetsFileType createEntity(EntityManager em) {
        LeassetsFileType leassetsFileType = new LeassetsFileType()
            .leassetsFileTypeName(DEFAULT_LEASSETS_FILE_TYPE_NAME)
            .leassetsFileMediumType(DEFAULT_LEASSETS_FILE_MEDIUM_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .fileTemplate(DEFAULT_FILE_TEMPLATE)
            .fileTemplateContentType(DEFAULT_FILE_TEMPLATE_CONTENT_TYPE)
            .leassetsfileType(DEFAULT_LEASSETSFILE_TYPE);
        return leassetsFileType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeassetsFileType createUpdatedEntity(EntityManager em) {
        LeassetsFileType leassetsFileType = new LeassetsFileType()
            .leassetsFileTypeName(UPDATED_LEASSETS_FILE_TYPE_NAME)
            .leassetsFileMediumType(UPDATED_LEASSETS_FILE_MEDIUM_TYPE)
            .description(UPDATED_DESCRIPTION)
            .fileTemplate(UPDATED_FILE_TEMPLATE)
            .fileTemplateContentType(UPDATED_FILE_TEMPLATE_CONTENT_TYPE)
            .leassetsfileType(UPDATED_LEASSETSFILE_TYPE);
        return leassetsFileType;
    }

    @BeforeEach
    public void initTest() {
        leassetsFileType = createEntity(em);
    }

    @Test
    @Transactional
    void createLeassetsFileType() throws Exception {
        int databaseSizeBeforeCreate = leassetsFileTypeRepository.findAll().size();
        // Create the LeassetsFileType
        restLeassetsFileTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leassetsFileType))
            )
            .andExpect(status().isCreated());

        // Validate the LeassetsFileType in the database
        List<LeassetsFileType> leassetsFileTypeList = leassetsFileTypeRepository.findAll();
        assertThat(leassetsFileTypeList).hasSize(databaseSizeBeforeCreate + 1);
        LeassetsFileType testLeassetsFileType = leassetsFileTypeList.get(leassetsFileTypeList.size() - 1);
        assertThat(testLeassetsFileType.getLeassetsFileTypeName()).isEqualTo(DEFAULT_LEASSETS_FILE_TYPE_NAME);
        assertThat(testLeassetsFileType.getLeassetsFileMediumType()).isEqualTo(DEFAULT_LEASSETS_FILE_MEDIUM_TYPE);
        assertThat(testLeassetsFileType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLeassetsFileType.getFileTemplate()).isEqualTo(DEFAULT_FILE_TEMPLATE);
        assertThat(testLeassetsFileType.getFileTemplateContentType()).isEqualTo(DEFAULT_FILE_TEMPLATE_CONTENT_TYPE);
        assertThat(testLeassetsFileType.getLeassetsfileType()).isEqualTo(DEFAULT_LEASSETSFILE_TYPE);

        // Validate the LeassetsFileType in Elasticsearch
        verify(mockLeassetsFileTypeSearchRepository, times(1)).save(testLeassetsFileType);
    }

    @Test
    @Transactional
    void createLeassetsFileTypeWithExistingId() throws Exception {
        // Create the LeassetsFileType with an existing ID
        leassetsFileType.setId(1L);

        int databaseSizeBeforeCreate = leassetsFileTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeassetsFileTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leassetsFileType))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeassetsFileType in the database
        List<LeassetsFileType> leassetsFileTypeList = leassetsFileTypeRepository.findAll();
        assertThat(leassetsFileTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the LeassetsFileType in Elasticsearch
        verify(mockLeassetsFileTypeSearchRepository, times(0)).save(leassetsFileType);
    }

    @Test
    @Transactional
    void checkLeassetsFileTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = leassetsFileTypeRepository.findAll().size();
        // set the field null
        leassetsFileType.setLeassetsFileTypeName(null);

        // Create the LeassetsFileType, which fails.

        restLeassetsFileTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leassetsFileType))
            )
            .andExpect(status().isBadRequest());

        List<LeassetsFileType> leassetsFileTypeList = leassetsFileTypeRepository.findAll();
        assertThat(leassetsFileTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLeassetsFileMediumTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = leassetsFileTypeRepository.findAll().size();
        // set the field null
        leassetsFileType.setLeassetsFileMediumType(null);

        // Create the LeassetsFileType, which fails.

        restLeassetsFileTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leassetsFileType))
            )
            .andExpect(status().isBadRequest());

        List<LeassetsFileType> leassetsFileTypeList = leassetsFileTypeRepository.findAll();
        assertThat(leassetsFileTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypes() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList
        restLeassetsFileTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leassetsFileType.getId().intValue())))
            .andExpect(jsonPath("$.[*].leassetsFileTypeName").value(hasItem(DEFAULT_LEASSETS_FILE_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].leassetsFileMediumType").value(hasItem(DEFAULT_LEASSETS_FILE_MEDIUM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fileTemplateContentType").value(hasItem(DEFAULT_FILE_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fileTemplate").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE_TEMPLATE))))
            .andExpect(jsonPath("$.[*].leassetsfileType").value(hasItem(DEFAULT_LEASSETSFILE_TYPE.toString())));
    }

    @Test
    @Transactional
    void getLeassetsFileType() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get the leassetsFileType
        restLeassetsFileTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, leassetsFileType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leassetsFileType.getId().intValue()))
            .andExpect(jsonPath("$.leassetsFileTypeName").value(DEFAULT_LEASSETS_FILE_TYPE_NAME))
            .andExpect(jsonPath("$.leassetsFileMediumType").value(DEFAULT_LEASSETS_FILE_MEDIUM_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.fileTemplateContentType").value(DEFAULT_FILE_TEMPLATE_CONTENT_TYPE))
            .andExpect(jsonPath("$.fileTemplate").value(Base64Utils.encodeToString(DEFAULT_FILE_TEMPLATE)))
            .andExpect(jsonPath("$.leassetsfileType").value(DEFAULT_LEASSETSFILE_TYPE.toString()));
    }

    @Test
    @Transactional
    void getLeassetsFileTypesByIdFiltering() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        Long id = leassetsFileType.getId();

        defaultLeassetsFileTypeShouldBeFound("id.equals=" + id);
        defaultLeassetsFileTypeShouldNotBeFound("id.notEquals=" + id);

        defaultLeassetsFileTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeassetsFileTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultLeassetsFileTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeassetsFileTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypesByLeassetsFileTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList where leassetsFileTypeName equals to DEFAULT_LEASSETS_FILE_TYPE_NAME
        defaultLeassetsFileTypeShouldBeFound("leassetsFileTypeName.equals=" + DEFAULT_LEASSETS_FILE_TYPE_NAME);

        // Get all the leassetsFileTypeList where leassetsFileTypeName equals to UPDATED_LEASSETS_FILE_TYPE_NAME
        defaultLeassetsFileTypeShouldNotBeFound("leassetsFileTypeName.equals=" + UPDATED_LEASSETS_FILE_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypesByLeassetsFileTypeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList where leassetsFileTypeName not equals to DEFAULT_LEASSETS_FILE_TYPE_NAME
        defaultLeassetsFileTypeShouldNotBeFound("leassetsFileTypeName.notEquals=" + DEFAULT_LEASSETS_FILE_TYPE_NAME);

        // Get all the leassetsFileTypeList where leassetsFileTypeName not equals to UPDATED_LEASSETS_FILE_TYPE_NAME
        defaultLeassetsFileTypeShouldBeFound("leassetsFileTypeName.notEquals=" + UPDATED_LEASSETS_FILE_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypesByLeassetsFileTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList where leassetsFileTypeName in DEFAULT_LEASSETS_FILE_TYPE_NAME or UPDATED_LEASSETS_FILE_TYPE_NAME
        defaultLeassetsFileTypeShouldBeFound(
            "leassetsFileTypeName.in=" + DEFAULT_LEASSETS_FILE_TYPE_NAME + "," + UPDATED_LEASSETS_FILE_TYPE_NAME
        );

        // Get all the leassetsFileTypeList where leassetsFileTypeName equals to UPDATED_LEASSETS_FILE_TYPE_NAME
        defaultLeassetsFileTypeShouldNotBeFound("leassetsFileTypeName.in=" + UPDATED_LEASSETS_FILE_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypesByLeassetsFileTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList where leassetsFileTypeName is not null
        defaultLeassetsFileTypeShouldBeFound("leassetsFileTypeName.specified=true");

        // Get all the leassetsFileTypeList where leassetsFileTypeName is null
        defaultLeassetsFileTypeShouldNotBeFound("leassetsFileTypeName.specified=false");
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypesByLeassetsFileTypeNameContainsSomething() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList where leassetsFileTypeName contains DEFAULT_LEASSETS_FILE_TYPE_NAME
        defaultLeassetsFileTypeShouldBeFound("leassetsFileTypeName.contains=" + DEFAULT_LEASSETS_FILE_TYPE_NAME);

        // Get all the leassetsFileTypeList where leassetsFileTypeName contains UPDATED_LEASSETS_FILE_TYPE_NAME
        defaultLeassetsFileTypeShouldNotBeFound("leassetsFileTypeName.contains=" + UPDATED_LEASSETS_FILE_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypesByLeassetsFileTypeNameNotContainsSomething() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList where leassetsFileTypeName does not contain DEFAULT_LEASSETS_FILE_TYPE_NAME
        defaultLeassetsFileTypeShouldNotBeFound("leassetsFileTypeName.doesNotContain=" + DEFAULT_LEASSETS_FILE_TYPE_NAME);

        // Get all the leassetsFileTypeList where leassetsFileTypeName does not contain UPDATED_LEASSETS_FILE_TYPE_NAME
        defaultLeassetsFileTypeShouldBeFound("leassetsFileTypeName.doesNotContain=" + UPDATED_LEASSETS_FILE_TYPE_NAME);
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypesByLeassetsFileMediumTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList where leassetsFileMediumType equals to DEFAULT_LEASSETS_FILE_MEDIUM_TYPE
        defaultLeassetsFileTypeShouldBeFound("leassetsFileMediumType.equals=" + DEFAULT_LEASSETS_FILE_MEDIUM_TYPE);

        // Get all the leassetsFileTypeList where leassetsFileMediumType equals to UPDATED_LEASSETS_FILE_MEDIUM_TYPE
        defaultLeassetsFileTypeShouldNotBeFound("leassetsFileMediumType.equals=" + UPDATED_LEASSETS_FILE_MEDIUM_TYPE);
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypesByLeassetsFileMediumTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList where leassetsFileMediumType not equals to DEFAULT_LEASSETS_FILE_MEDIUM_TYPE
        defaultLeassetsFileTypeShouldNotBeFound("leassetsFileMediumType.notEquals=" + DEFAULT_LEASSETS_FILE_MEDIUM_TYPE);

        // Get all the leassetsFileTypeList where leassetsFileMediumType not equals to UPDATED_LEASSETS_FILE_MEDIUM_TYPE
        defaultLeassetsFileTypeShouldBeFound("leassetsFileMediumType.notEquals=" + UPDATED_LEASSETS_FILE_MEDIUM_TYPE);
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypesByLeassetsFileMediumTypeIsInShouldWork() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList where leassetsFileMediumType in DEFAULT_LEASSETS_FILE_MEDIUM_TYPE or UPDATED_LEASSETS_FILE_MEDIUM_TYPE
        defaultLeassetsFileTypeShouldBeFound(
            "leassetsFileMediumType.in=" + DEFAULT_LEASSETS_FILE_MEDIUM_TYPE + "," + UPDATED_LEASSETS_FILE_MEDIUM_TYPE
        );

        // Get all the leassetsFileTypeList where leassetsFileMediumType equals to UPDATED_LEASSETS_FILE_MEDIUM_TYPE
        defaultLeassetsFileTypeShouldNotBeFound("leassetsFileMediumType.in=" + UPDATED_LEASSETS_FILE_MEDIUM_TYPE);
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypesByLeassetsFileMediumTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList where leassetsFileMediumType is not null
        defaultLeassetsFileTypeShouldBeFound("leassetsFileMediumType.specified=true");

        // Get all the leassetsFileTypeList where leassetsFileMediumType is null
        defaultLeassetsFileTypeShouldNotBeFound("leassetsFileMediumType.specified=false");
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList where description equals to DEFAULT_DESCRIPTION
        defaultLeassetsFileTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the leassetsFileTypeList where description equals to UPDATED_DESCRIPTION
        defaultLeassetsFileTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultLeassetsFileTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the leassetsFileTypeList where description not equals to UPDATED_DESCRIPTION
        defaultLeassetsFileTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultLeassetsFileTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the leassetsFileTypeList where description equals to UPDATED_DESCRIPTION
        defaultLeassetsFileTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList where description is not null
        defaultLeassetsFileTypeShouldBeFound("description.specified=true");

        // Get all the leassetsFileTypeList where description is null
        defaultLeassetsFileTypeShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList where description contains DEFAULT_DESCRIPTION
        defaultLeassetsFileTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the leassetsFileTypeList where description contains UPDATED_DESCRIPTION
        defaultLeassetsFileTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultLeassetsFileTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the leassetsFileTypeList where description does not contain UPDATED_DESCRIPTION
        defaultLeassetsFileTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypesByLeassetsfileTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList where leassetsfileType equals to DEFAULT_LEASSETSFILE_TYPE
        defaultLeassetsFileTypeShouldBeFound("leassetsfileType.equals=" + DEFAULT_LEASSETSFILE_TYPE);

        // Get all the leassetsFileTypeList where leassetsfileType equals to UPDATED_LEASSETSFILE_TYPE
        defaultLeassetsFileTypeShouldNotBeFound("leassetsfileType.equals=" + UPDATED_LEASSETSFILE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypesByLeassetsfileTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList where leassetsfileType not equals to DEFAULT_LEASSETSFILE_TYPE
        defaultLeassetsFileTypeShouldNotBeFound("leassetsfileType.notEquals=" + DEFAULT_LEASSETSFILE_TYPE);

        // Get all the leassetsFileTypeList where leassetsfileType not equals to UPDATED_LEASSETSFILE_TYPE
        defaultLeassetsFileTypeShouldBeFound("leassetsfileType.notEquals=" + UPDATED_LEASSETSFILE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypesByLeassetsfileTypeIsInShouldWork() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList where leassetsfileType in DEFAULT_LEASSETSFILE_TYPE or UPDATED_LEASSETSFILE_TYPE
        defaultLeassetsFileTypeShouldBeFound("leassetsfileType.in=" + DEFAULT_LEASSETSFILE_TYPE + "," + UPDATED_LEASSETSFILE_TYPE);

        // Get all the leassetsFileTypeList where leassetsfileType equals to UPDATED_LEASSETSFILE_TYPE
        defaultLeassetsFileTypeShouldNotBeFound("leassetsfileType.in=" + UPDATED_LEASSETSFILE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeassetsFileTypesByLeassetsfileTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        // Get all the leassetsFileTypeList where leassetsfileType is not null
        defaultLeassetsFileTypeShouldBeFound("leassetsfileType.specified=true");

        // Get all the leassetsFileTypeList where leassetsfileType is null
        defaultLeassetsFileTypeShouldNotBeFound("leassetsfileType.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeassetsFileTypeShouldBeFound(String filter) throws Exception {
        restLeassetsFileTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leassetsFileType.getId().intValue())))
            .andExpect(jsonPath("$.[*].leassetsFileTypeName").value(hasItem(DEFAULT_LEASSETS_FILE_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].leassetsFileMediumType").value(hasItem(DEFAULT_LEASSETS_FILE_MEDIUM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fileTemplateContentType").value(hasItem(DEFAULT_FILE_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fileTemplate").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE_TEMPLATE))))
            .andExpect(jsonPath("$.[*].leassetsfileType").value(hasItem(DEFAULT_LEASSETSFILE_TYPE.toString())));

        // Check, that the count call also returns 1
        restLeassetsFileTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeassetsFileTypeShouldNotBeFound(String filter) throws Exception {
        restLeassetsFileTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeassetsFileTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeassetsFileType() throws Exception {
        // Get the leassetsFileType
        restLeassetsFileTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLeassetsFileType() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        int databaseSizeBeforeUpdate = leassetsFileTypeRepository.findAll().size();

        // Update the leassetsFileType
        LeassetsFileType updatedLeassetsFileType = leassetsFileTypeRepository.findById(leassetsFileType.getId()).get();
        // Disconnect from session so that the updates on updatedLeassetsFileType are not directly saved in db
        em.detach(updatedLeassetsFileType);
        updatedLeassetsFileType
            .leassetsFileTypeName(UPDATED_LEASSETS_FILE_TYPE_NAME)
            .leassetsFileMediumType(UPDATED_LEASSETS_FILE_MEDIUM_TYPE)
            .description(UPDATED_DESCRIPTION)
            .fileTemplate(UPDATED_FILE_TEMPLATE)
            .fileTemplateContentType(UPDATED_FILE_TEMPLATE_CONTENT_TYPE)
            .leassetsfileType(UPDATED_LEASSETSFILE_TYPE);

        restLeassetsFileTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLeassetsFileType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLeassetsFileType))
            )
            .andExpect(status().isOk());

        // Validate the LeassetsFileType in the database
        List<LeassetsFileType> leassetsFileTypeList = leassetsFileTypeRepository.findAll();
        assertThat(leassetsFileTypeList).hasSize(databaseSizeBeforeUpdate);
        LeassetsFileType testLeassetsFileType = leassetsFileTypeList.get(leassetsFileTypeList.size() - 1);
        assertThat(testLeassetsFileType.getLeassetsFileTypeName()).isEqualTo(UPDATED_LEASSETS_FILE_TYPE_NAME);
        assertThat(testLeassetsFileType.getLeassetsFileMediumType()).isEqualTo(UPDATED_LEASSETS_FILE_MEDIUM_TYPE);
        assertThat(testLeassetsFileType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLeassetsFileType.getFileTemplate()).isEqualTo(UPDATED_FILE_TEMPLATE);
        assertThat(testLeassetsFileType.getFileTemplateContentType()).isEqualTo(UPDATED_FILE_TEMPLATE_CONTENT_TYPE);
        assertThat(testLeassetsFileType.getLeassetsfileType()).isEqualTo(UPDATED_LEASSETSFILE_TYPE);

        // Validate the LeassetsFileType in Elasticsearch
        verify(mockLeassetsFileTypeSearchRepository).save(testLeassetsFileType);
    }

    @Test
    @Transactional
    void putNonExistingLeassetsFileType() throws Exception {
        int databaseSizeBeforeUpdate = leassetsFileTypeRepository.findAll().size();
        leassetsFileType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeassetsFileTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leassetsFileType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leassetsFileType))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeassetsFileType in the database
        List<LeassetsFileType> leassetsFileTypeList = leassetsFileTypeRepository.findAll();
        assertThat(leassetsFileTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LeassetsFileType in Elasticsearch
        verify(mockLeassetsFileTypeSearchRepository, times(0)).save(leassetsFileType);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeassetsFileType() throws Exception {
        int databaseSizeBeforeUpdate = leassetsFileTypeRepository.findAll().size();
        leassetsFileType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeassetsFileTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leassetsFileType))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeassetsFileType in the database
        List<LeassetsFileType> leassetsFileTypeList = leassetsFileTypeRepository.findAll();
        assertThat(leassetsFileTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LeassetsFileType in Elasticsearch
        verify(mockLeassetsFileTypeSearchRepository, times(0)).save(leassetsFileType);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeassetsFileType() throws Exception {
        int databaseSizeBeforeUpdate = leassetsFileTypeRepository.findAll().size();
        leassetsFileType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeassetsFileTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leassetsFileType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeassetsFileType in the database
        List<LeassetsFileType> leassetsFileTypeList = leassetsFileTypeRepository.findAll();
        assertThat(leassetsFileTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LeassetsFileType in Elasticsearch
        verify(mockLeassetsFileTypeSearchRepository, times(0)).save(leassetsFileType);
    }

    @Test
    @Transactional
    void partialUpdateLeassetsFileTypeWithPatch() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        int databaseSizeBeforeUpdate = leassetsFileTypeRepository.findAll().size();

        // Update the leassetsFileType using partial update
        LeassetsFileType partialUpdatedLeassetsFileType = new LeassetsFileType();
        partialUpdatedLeassetsFileType.setId(leassetsFileType.getId());

        partialUpdatedLeassetsFileType
            .leassetsFileTypeName(UPDATED_LEASSETS_FILE_TYPE_NAME)
            .description(UPDATED_DESCRIPTION)
            .fileTemplate(UPDATED_FILE_TEMPLATE)
            .fileTemplateContentType(UPDATED_FILE_TEMPLATE_CONTENT_TYPE);

        restLeassetsFileTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeassetsFileType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeassetsFileType))
            )
            .andExpect(status().isOk());

        // Validate the LeassetsFileType in the database
        List<LeassetsFileType> leassetsFileTypeList = leassetsFileTypeRepository.findAll();
        assertThat(leassetsFileTypeList).hasSize(databaseSizeBeforeUpdate);
        LeassetsFileType testLeassetsFileType = leassetsFileTypeList.get(leassetsFileTypeList.size() - 1);
        assertThat(testLeassetsFileType.getLeassetsFileTypeName()).isEqualTo(UPDATED_LEASSETS_FILE_TYPE_NAME);
        assertThat(testLeassetsFileType.getLeassetsFileMediumType()).isEqualTo(DEFAULT_LEASSETS_FILE_MEDIUM_TYPE);
        assertThat(testLeassetsFileType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLeassetsFileType.getFileTemplate()).isEqualTo(UPDATED_FILE_TEMPLATE);
        assertThat(testLeassetsFileType.getFileTemplateContentType()).isEqualTo(UPDATED_FILE_TEMPLATE_CONTENT_TYPE);
        assertThat(testLeassetsFileType.getLeassetsfileType()).isEqualTo(DEFAULT_LEASSETSFILE_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateLeassetsFileTypeWithPatch() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        int databaseSizeBeforeUpdate = leassetsFileTypeRepository.findAll().size();

        // Update the leassetsFileType using partial update
        LeassetsFileType partialUpdatedLeassetsFileType = new LeassetsFileType();
        partialUpdatedLeassetsFileType.setId(leassetsFileType.getId());

        partialUpdatedLeassetsFileType
            .leassetsFileTypeName(UPDATED_LEASSETS_FILE_TYPE_NAME)
            .leassetsFileMediumType(UPDATED_LEASSETS_FILE_MEDIUM_TYPE)
            .description(UPDATED_DESCRIPTION)
            .fileTemplate(UPDATED_FILE_TEMPLATE)
            .fileTemplateContentType(UPDATED_FILE_TEMPLATE_CONTENT_TYPE)
            .leassetsfileType(UPDATED_LEASSETSFILE_TYPE);

        restLeassetsFileTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeassetsFileType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeassetsFileType))
            )
            .andExpect(status().isOk());

        // Validate the LeassetsFileType in the database
        List<LeassetsFileType> leassetsFileTypeList = leassetsFileTypeRepository.findAll();
        assertThat(leassetsFileTypeList).hasSize(databaseSizeBeforeUpdate);
        LeassetsFileType testLeassetsFileType = leassetsFileTypeList.get(leassetsFileTypeList.size() - 1);
        assertThat(testLeassetsFileType.getLeassetsFileTypeName()).isEqualTo(UPDATED_LEASSETS_FILE_TYPE_NAME);
        assertThat(testLeassetsFileType.getLeassetsFileMediumType()).isEqualTo(UPDATED_LEASSETS_FILE_MEDIUM_TYPE);
        assertThat(testLeassetsFileType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLeassetsFileType.getFileTemplate()).isEqualTo(UPDATED_FILE_TEMPLATE);
        assertThat(testLeassetsFileType.getFileTemplateContentType()).isEqualTo(UPDATED_FILE_TEMPLATE_CONTENT_TYPE);
        assertThat(testLeassetsFileType.getLeassetsfileType()).isEqualTo(UPDATED_LEASSETSFILE_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingLeassetsFileType() throws Exception {
        int databaseSizeBeforeUpdate = leassetsFileTypeRepository.findAll().size();
        leassetsFileType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeassetsFileTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leassetsFileType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leassetsFileType))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeassetsFileType in the database
        List<LeassetsFileType> leassetsFileTypeList = leassetsFileTypeRepository.findAll();
        assertThat(leassetsFileTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LeassetsFileType in Elasticsearch
        verify(mockLeassetsFileTypeSearchRepository, times(0)).save(leassetsFileType);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeassetsFileType() throws Exception {
        int databaseSizeBeforeUpdate = leassetsFileTypeRepository.findAll().size();
        leassetsFileType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeassetsFileTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leassetsFileType))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeassetsFileType in the database
        List<LeassetsFileType> leassetsFileTypeList = leassetsFileTypeRepository.findAll();
        assertThat(leassetsFileTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LeassetsFileType in Elasticsearch
        verify(mockLeassetsFileTypeSearchRepository, times(0)).save(leassetsFileType);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeassetsFileType() throws Exception {
        int databaseSizeBeforeUpdate = leassetsFileTypeRepository.findAll().size();
        leassetsFileType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeassetsFileTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leassetsFileType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeassetsFileType in the database
        List<LeassetsFileType> leassetsFileTypeList = leassetsFileTypeRepository.findAll();
        assertThat(leassetsFileTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LeassetsFileType in Elasticsearch
        verify(mockLeassetsFileTypeSearchRepository, times(0)).save(leassetsFileType);
    }

    @Test
    @Transactional
    void deleteLeassetsFileType() throws Exception {
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);

        int databaseSizeBeforeDelete = leassetsFileTypeRepository.findAll().size();

        // Delete the leassetsFileType
        restLeassetsFileTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, leassetsFileType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeassetsFileType> leassetsFileTypeList = leassetsFileTypeRepository.findAll();
        assertThat(leassetsFileTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LeassetsFileType in Elasticsearch
        verify(mockLeassetsFileTypeSearchRepository, times(1)).deleteById(leassetsFileType.getId());
    }

    @Test
    @Transactional
    void searchLeassetsFileType() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        leassetsFileTypeRepository.saveAndFlush(leassetsFileType);
        when(mockLeassetsFileTypeSearchRepository.search(queryStringQuery("id:" + leassetsFileType.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(leassetsFileType), PageRequest.of(0, 1), 1));

        // Search the leassetsFileType
        restLeassetsFileTypeMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + leassetsFileType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leassetsFileType.getId().intValue())))
            .andExpect(jsonPath("$.[*].leassetsFileTypeName").value(hasItem(DEFAULT_LEASSETS_FILE_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].leassetsFileMediumType").value(hasItem(DEFAULT_LEASSETS_FILE_MEDIUM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fileTemplateContentType").value(hasItem(DEFAULT_FILE_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fileTemplate").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE_TEMPLATE))))
            .andExpect(jsonPath("$.[*].leassetsfileType").value(hasItem(DEFAULT_LEASSETSFILE_TYPE.toString())));
    }
}
