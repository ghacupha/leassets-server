package tech.leassets.web.rest;

import tech.leassets.LeassetsServerApp;
import tech.leassets.domain.LeassetsFileUpload;
import tech.leassets.repository.LeassetsFileUploadRepository;
import tech.leassets.repository.search.LeassetsFileUploadSearchRepository;
import tech.leassets.service.LeassetsFileUploadService;
import tech.leassets.service.dto.LeassetsFileUploadDTO;
import tech.leassets.service.mapper.LeassetsFileUploadMapper;
import tech.leassets.service.dto.LeassetsFileUploadCriteria;
import tech.leassets.service.LeassetsFileUploadQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LeassetsFileUploadResource} REST controller.
 */
@SpringBootTest(classes = LeassetsServerApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class LeassetsFileUploadResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PERIOD_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PERIOD_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PERIOD_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_PERIOD_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PERIOD_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PERIOD_TO = LocalDate.ofEpochDay(-1L);

    private static final Long DEFAULT_LEASSETS_FILE_TYPE_ID = 1L;
    private static final Long UPDATED_LEASSETS_FILE_TYPE_ID = 2L;
    private static final Long SMALLER_LEASSETS_FILE_TYPE_ID = 1L - 1L;

    private static final byte[] DEFAULT_DATA_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_FILE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_UPLOAD_SUCCESSFUL = false;
    private static final Boolean UPDATED_UPLOAD_SUCCESSFUL = true;

    private static final Boolean DEFAULT_UPLOAD_PROCESSED = false;
    private static final Boolean UPDATED_UPLOAD_PROCESSED = true;

    private static final String DEFAULT_UPLOAD_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_UPLOAD_TOKEN = "BBBBBBBBBB";

    @Autowired
    private LeassetsFileUploadRepository leassetsFileUploadRepository;

    @Autowired
    private LeassetsFileUploadMapper leassetsFileUploadMapper;

    @Autowired
    private LeassetsFileUploadService leassetsFileUploadService;

    /**
     * This repository is mocked in the tech.leassets.repository.search test package.
     *
     * @see tech.leassets.repository.search.LeassetsFileUploadSearchRepositoryMockConfiguration
     */
    @Autowired
    private LeassetsFileUploadSearchRepository mockLeassetsFileUploadSearchRepository;

    @Autowired
    private LeassetsFileUploadQueryService leassetsFileUploadQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeassetsFileUploadMockMvc;

    private LeassetsFileUpload leassetsFileUpload;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeassetsFileUpload createEntity(EntityManager em) {
        LeassetsFileUpload leassetsFileUpload = new LeassetsFileUpload()
            .description(DEFAULT_DESCRIPTION)
            .fileName(DEFAULT_FILE_NAME)
            .periodFrom(DEFAULT_PERIOD_FROM)
            .periodTo(DEFAULT_PERIOD_TO)
            .leassetsFileTypeId(DEFAULT_LEASSETS_FILE_TYPE_ID)
            .dataFile(DEFAULT_DATA_FILE)
            .dataFileContentType(DEFAULT_DATA_FILE_CONTENT_TYPE)
            .uploadSuccessful(DEFAULT_UPLOAD_SUCCESSFUL)
            .uploadProcessed(DEFAULT_UPLOAD_PROCESSED)
            .uploadToken(DEFAULT_UPLOAD_TOKEN);
        return leassetsFileUpload;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeassetsFileUpload createUpdatedEntity(EntityManager em) {
        LeassetsFileUpload leassetsFileUpload = new LeassetsFileUpload()
            .description(UPDATED_DESCRIPTION)
            .fileName(UPDATED_FILE_NAME)
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .leassetsFileTypeId(UPDATED_LEASSETS_FILE_TYPE_ID)
            .dataFile(UPDATED_DATA_FILE)
            .dataFileContentType(UPDATED_DATA_FILE_CONTENT_TYPE)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL)
            .uploadProcessed(UPDATED_UPLOAD_PROCESSED)
            .uploadToken(UPDATED_UPLOAD_TOKEN);
        return leassetsFileUpload;
    }

    @BeforeEach
    public void initTest() {
        leassetsFileUpload = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeassetsFileUpload() throws Exception {
        int databaseSizeBeforeCreate = leassetsFileUploadRepository.findAll().size();
        // Create the LeassetsFileUpload
        LeassetsFileUploadDTO leassetsFileUploadDTO = leassetsFileUploadMapper.toDto(leassetsFileUpload);
        restLeassetsFileUploadMockMvc.perform(post("/api/leassets-file-uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leassetsFileUploadDTO)))
            .andExpect(status().isCreated());

        // Validate the LeassetsFileUpload in the database
        List<LeassetsFileUpload> leassetsFileUploadList = leassetsFileUploadRepository.findAll();
        assertThat(leassetsFileUploadList).hasSize(databaseSizeBeforeCreate + 1);
        LeassetsFileUpload testLeassetsFileUpload = leassetsFileUploadList.get(leassetsFileUploadList.size() - 1);
        assertThat(testLeassetsFileUpload.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLeassetsFileUpload.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testLeassetsFileUpload.getPeriodFrom()).isEqualTo(DEFAULT_PERIOD_FROM);
        assertThat(testLeassetsFileUpload.getPeriodTo()).isEqualTo(DEFAULT_PERIOD_TO);
        assertThat(testLeassetsFileUpload.getLeassetsFileTypeId()).isEqualTo(DEFAULT_LEASSETS_FILE_TYPE_ID);
        assertThat(testLeassetsFileUpload.getDataFile()).isEqualTo(DEFAULT_DATA_FILE);
        assertThat(testLeassetsFileUpload.getDataFileContentType()).isEqualTo(DEFAULT_DATA_FILE_CONTENT_TYPE);
        assertThat(testLeassetsFileUpload.isUploadSuccessful()).isEqualTo(DEFAULT_UPLOAD_SUCCESSFUL);
        assertThat(testLeassetsFileUpload.isUploadProcessed()).isEqualTo(DEFAULT_UPLOAD_PROCESSED);
        assertThat(testLeassetsFileUpload.getUploadToken()).isEqualTo(DEFAULT_UPLOAD_TOKEN);

        // Validate the LeassetsFileUpload in Elasticsearch
        verify(mockLeassetsFileUploadSearchRepository, times(1)).save(testLeassetsFileUpload);
    }

    @Test
    @Transactional
    public void createLeassetsFileUploadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leassetsFileUploadRepository.findAll().size();

        // Create the LeassetsFileUpload with an existing ID
        leassetsFileUpload.setId(1L);
        LeassetsFileUploadDTO leassetsFileUploadDTO = leassetsFileUploadMapper.toDto(leassetsFileUpload);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeassetsFileUploadMockMvc.perform(post("/api/leassets-file-uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leassetsFileUploadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeassetsFileUpload in the database
        List<LeassetsFileUpload> leassetsFileUploadList = leassetsFileUploadRepository.findAll();
        assertThat(leassetsFileUploadList).hasSize(databaseSizeBeforeCreate);

        // Validate the LeassetsFileUpload in Elasticsearch
        verify(mockLeassetsFileUploadSearchRepository, times(0)).save(leassetsFileUpload);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = leassetsFileUploadRepository.findAll().size();
        // set the field null
        leassetsFileUpload.setDescription(null);

        // Create the LeassetsFileUpload, which fails.
        LeassetsFileUploadDTO leassetsFileUploadDTO = leassetsFileUploadMapper.toDto(leassetsFileUpload);


        restLeassetsFileUploadMockMvc.perform(post("/api/leassets-file-uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leassetsFileUploadDTO)))
            .andExpect(status().isBadRequest());

        List<LeassetsFileUpload> leassetsFileUploadList = leassetsFileUploadRepository.findAll();
        assertThat(leassetsFileUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = leassetsFileUploadRepository.findAll().size();
        // set the field null
        leassetsFileUpload.setFileName(null);

        // Create the LeassetsFileUpload, which fails.
        LeassetsFileUploadDTO leassetsFileUploadDTO = leassetsFileUploadMapper.toDto(leassetsFileUpload);


        restLeassetsFileUploadMockMvc.perform(post("/api/leassets-file-uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leassetsFileUploadDTO)))
            .andExpect(status().isBadRequest());

        List<LeassetsFileUpload> leassetsFileUploadList = leassetsFileUploadRepository.findAll();
        assertThat(leassetsFileUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLeassetsFileTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = leassetsFileUploadRepository.findAll().size();
        // set the field null
        leassetsFileUpload.setLeassetsFileTypeId(null);

        // Create the LeassetsFileUpload, which fails.
        LeassetsFileUploadDTO leassetsFileUploadDTO = leassetsFileUploadMapper.toDto(leassetsFileUpload);


        restLeassetsFileUploadMockMvc.perform(post("/api/leassets-file-uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leassetsFileUploadDTO)))
            .andExpect(status().isBadRequest());

        List<LeassetsFileUpload> leassetsFileUploadList = leassetsFileUploadRepository.findAll();
        assertThat(leassetsFileUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploads() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList
        restLeassetsFileUploadMockMvc.perform(get("/api/leassets-file-uploads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leassetsFileUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].leassetsFileTypeId").value(hasItem(DEFAULT_LEASSETS_FILE_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].dataFileContentType").value(hasItem(DEFAULT_DATA_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadToken").value(hasItem(DEFAULT_UPLOAD_TOKEN)));
    }
    
    @Test
    @Transactional
    public void getLeassetsFileUpload() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get the leassetsFileUpload
        restLeassetsFileUploadMockMvc.perform(get("/api/leassets-file-uploads/{id}", leassetsFileUpload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leassetsFileUpload.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.periodFrom").value(DEFAULT_PERIOD_FROM.toString()))
            .andExpect(jsonPath("$.periodTo").value(DEFAULT_PERIOD_TO.toString()))
            .andExpect(jsonPath("$.leassetsFileTypeId").value(DEFAULT_LEASSETS_FILE_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.dataFileContentType").value(DEFAULT_DATA_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.dataFile").value(Base64Utils.encodeToString(DEFAULT_DATA_FILE)))
            .andExpect(jsonPath("$.uploadSuccessful").value(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue()))
            .andExpect(jsonPath("$.uploadProcessed").value(DEFAULT_UPLOAD_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.uploadToken").value(DEFAULT_UPLOAD_TOKEN));
    }


    @Test
    @Transactional
    public void getLeassetsFileUploadsByIdFiltering() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        Long id = leassetsFileUpload.getId();

        defaultLeassetsFileUploadShouldBeFound("id.equals=" + id);
        defaultLeassetsFileUploadShouldNotBeFound("id.notEquals=" + id);

        defaultLeassetsFileUploadShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeassetsFileUploadShouldNotBeFound("id.greaterThan=" + id);

        defaultLeassetsFileUploadShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeassetsFileUploadShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where description equals to DEFAULT_DESCRIPTION
        defaultLeassetsFileUploadShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the leassetsFileUploadList where description equals to UPDATED_DESCRIPTION
        defaultLeassetsFileUploadShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where description not equals to DEFAULT_DESCRIPTION
        defaultLeassetsFileUploadShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the leassetsFileUploadList where description not equals to UPDATED_DESCRIPTION
        defaultLeassetsFileUploadShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultLeassetsFileUploadShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the leassetsFileUploadList where description equals to UPDATED_DESCRIPTION
        defaultLeassetsFileUploadShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where description is not null
        defaultLeassetsFileUploadShouldBeFound("description.specified=true");

        // Get all the leassetsFileUploadList where description is null
        defaultLeassetsFileUploadShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllLeassetsFileUploadsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where description contains DEFAULT_DESCRIPTION
        defaultLeassetsFileUploadShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the leassetsFileUploadList where description contains UPDATED_DESCRIPTION
        defaultLeassetsFileUploadShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where description does not contain DEFAULT_DESCRIPTION
        defaultLeassetsFileUploadShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the leassetsFileUploadList where description does not contain UPDATED_DESCRIPTION
        defaultLeassetsFileUploadShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where fileName equals to DEFAULT_FILE_NAME
        defaultLeassetsFileUploadShouldBeFound("fileName.equals=" + DEFAULT_FILE_NAME);

        // Get all the leassetsFileUploadList where fileName equals to UPDATED_FILE_NAME
        defaultLeassetsFileUploadShouldNotBeFound("fileName.equals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByFileNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where fileName not equals to DEFAULT_FILE_NAME
        defaultLeassetsFileUploadShouldNotBeFound("fileName.notEquals=" + DEFAULT_FILE_NAME);

        // Get all the leassetsFileUploadList where fileName not equals to UPDATED_FILE_NAME
        defaultLeassetsFileUploadShouldBeFound("fileName.notEquals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where fileName in DEFAULT_FILE_NAME or UPDATED_FILE_NAME
        defaultLeassetsFileUploadShouldBeFound("fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME);

        // Get all the leassetsFileUploadList where fileName equals to UPDATED_FILE_NAME
        defaultLeassetsFileUploadShouldNotBeFound("fileName.in=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where fileName is not null
        defaultLeassetsFileUploadShouldBeFound("fileName.specified=true");

        // Get all the leassetsFileUploadList where fileName is null
        defaultLeassetsFileUploadShouldNotBeFound("fileName.specified=false");
    }
                @Test
    @Transactional
    public void getAllLeassetsFileUploadsByFileNameContainsSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where fileName contains DEFAULT_FILE_NAME
        defaultLeassetsFileUploadShouldBeFound("fileName.contains=" + DEFAULT_FILE_NAME);

        // Get all the leassetsFileUploadList where fileName contains UPDATED_FILE_NAME
        defaultLeassetsFileUploadShouldNotBeFound("fileName.contains=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByFileNameNotContainsSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where fileName does not contain DEFAULT_FILE_NAME
        defaultLeassetsFileUploadShouldNotBeFound("fileName.doesNotContain=" + DEFAULT_FILE_NAME);

        // Get all the leassetsFileUploadList where fileName does not contain UPDATED_FILE_NAME
        defaultLeassetsFileUploadShouldBeFound("fileName.doesNotContain=" + UPDATED_FILE_NAME);
    }


    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByPeriodFromIsEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where periodFrom equals to DEFAULT_PERIOD_FROM
        defaultLeassetsFileUploadShouldBeFound("periodFrom.equals=" + DEFAULT_PERIOD_FROM);

        // Get all the leassetsFileUploadList where periodFrom equals to UPDATED_PERIOD_FROM
        defaultLeassetsFileUploadShouldNotBeFound("periodFrom.equals=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByPeriodFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where periodFrom not equals to DEFAULT_PERIOD_FROM
        defaultLeassetsFileUploadShouldNotBeFound("periodFrom.notEquals=" + DEFAULT_PERIOD_FROM);

        // Get all the leassetsFileUploadList where periodFrom not equals to UPDATED_PERIOD_FROM
        defaultLeassetsFileUploadShouldBeFound("periodFrom.notEquals=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByPeriodFromIsInShouldWork() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where periodFrom in DEFAULT_PERIOD_FROM or UPDATED_PERIOD_FROM
        defaultLeassetsFileUploadShouldBeFound("periodFrom.in=" + DEFAULT_PERIOD_FROM + "," + UPDATED_PERIOD_FROM);

        // Get all the leassetsFileUploadList where periodFrom equals to UPDATED_PERIOD_FROM
        defaultLeassetsFileUploadShouldNotBeFound("periodFrom.in=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByPeriodFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where periodFrom is not null
        defaultLeassetsFileUploadShouldBeFound("periodFrom.specified=true");

        // Get all the leassetsFileUploadList where periodFrom is null
        defaultLeassetsFileUploadShouldNotBeFound("periodFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByPeriodFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where periodFrom is greater than or equal to DEFAULT_PERIOD_FROM
        defaultLeassetsFileUploadShouldBeFound("periodFrom.greaterThanOrEqual=" + DEFAULT_PERIOD_FROM);

        // Get all the leassetsFileUploadList where periodFrom is greater than or equal to UPDATED_PERIOD_FROM
        defaultLeassetsFileUploadShouldNotBeFound("periodFrom.greaterThanOrEqual=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByPeriodFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where periodFrom is less than or equal to DEFAULT_PERIOD_FROM
        defaultLeassetsFileUploadShouldBeFound("periodFrom.lessThanOrEqual=" + DEFAULT_PERIOD_FROM);

        // Get all the leassetsFileUploadList where periodFrom is less than or equal to SMALLER_PERIOD_FROM
        defaultLeassetsFileUploadShouldNotBeFound("periodFrom.lessThanOrEqual=" + SMALLER_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByPeriodFromIsLessThanSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where periodFrom is less than DEFAULT_PERIOD_FROM
        defaultLeassetsFileUploadShouldNotBeFound("periodFrom.lessThan=" + DEFAULT_PERIOD_FROM);

        // Get all the leassetsFileUploadList where periodFrom is less than UPDATED_PERIOD_FROM
        defaultLeassetsFileUploadShouldBeFound("periodFrom.lessThan=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByPeriodFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where periodFrom is greater than DEFAULT_PERIOD_FROM
        defaultLeassetsFileUploadShouldNotBeFound("periodFrom.greaterThan=" + DEFAULT_PERIOD_FROM);

        // Get all the leassetsFileUploadList where periodFrom is greater than SMALLER_PERIOD_FROM
        defaultLeassetsFileUploadShouldBeFound("periodFrom.greaterThan=" + SMALLER_PERIOD_FROM);
    }


    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByPeriodToIsEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where periodTo equals to DEFAULT_PERIOD_TO
        defaultLeassetsFileUploadShouldBeFound("periodTo.equals=" + DEFAULT_PERIOD_TO);

        // Get all the leassetsFileUploadList where periodTo equals to UPDATED_PERIOD_TO
        defaultLeassetsFileUploadShouldNotBeFound("periodTo.equals=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByPeriodToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where periodTo not equals to DEFAULT_PERIOD_TO
        defaultLeassetsFileUploadShouldNotBeFound("periodTo.notEquals=" + DEFAULT_PERIOD_TO);

        // Get all the leassetsFileUploadList where periodTo not equals to UPDATED_PERIOD_TO
        defaultLeassetsFileUploadShouldBeFound("periodTo.notEquals=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByPeriodToIsInShouldWork() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where periodTo in DEFAULT_PERIOD_TO or UPDATED_PERIOD_TO
        defaultLeassetsFileUploadShouldBeFound("periodTo.in=" + DEFAULT_PERIOD_TO + "," + UPDATED_PERIOD_TO);

        // Get all the leassetsFileUploadList where periodTo equals to UPDATED_PERIOD_TO
        defaultLeassetsFileUploadShouldNotBeFound("periodTo.in=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByPeriodToIsNullOrNotNull() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where periodTo is not null
        defaultLeassetsFileUploadShouldBeFound("periodTo.specified=true");

        // Get all the leassetsFileUploadList where periodTo is null
        defaultLeassetsFileUploadShouldNotBeFound("periodTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByPeriodToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where periodTo is greater than or equal to DEFAULT_PERIOD_TO
        defaultLeassetsFileUploadShouldBeFound("periodTo.greaterThanOrEqual=" + DEFAULT_PERIOD_TO);

        // Get all the leassetsFileUploadList where periodTo is greater than or equal to UPDATED_PERIOD_TO
        defaultLeassetsFileUploadShouldNotBeFound("periodTo.greaterThanOrEqual=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByPeriodToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where periodTo is less than or equal to DEFAULT_PERIOD_TO
        defaultLeassetsFileUploadShouldBeFound("periodTo.lessThanOrEqual=" + DEFAULT_PERIOD_TO);

        // Get all the leassetsFileUploadList where periodTo is less than or equal to SMALLER_PERIOD_TO
        defaultLeassetsFileUploadShouldNotBeFound("periodTo.lessThanOrEqual=" + SMALLER_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByPeriodToIsLessThanSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where periodTo is less than DEFAULT_PERIOD_TO
        defaultLeassetsFileUploadShouldNotBeFound("periodTo.lessThan=" + DEFAULT_PERIOD_TO);

        // Get all the leassetsFileUploadList where periodTo is less than UPDATED_PERIOD_TO
        defaultLeassetsFileUploadShouldBeFound("periodTo.lessThan=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByPeriodToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where periodTo is greater than DEFAULT_PERIOD_TO
        defaultLeassetsFileUploadShouldNotBeFound("periodTo.greaterThan=" + DEFAULT_PERIOD_TO);

        // Get all the leassetsFileUploadList where periodTo is greater than SMALLER_PERIOD_TO
        defaultLeassetsFileUploadShouldBeFound("periodTo.greaterThan=" + SMALLER_PERIOD_TO);
    }


    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByLeassetsFileTypeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where leassetsFileTypeId equals to DEFAULT_LEASSETS_FILE_TYPE_ID
        defaultLeassetsFileUploadShouldBeFound("leassetsFileTypeId.equals=" + DEFAULT_LEASSETS_FILE_TYPE_ID);

        // Get all the leassetsFileUploadList where leassetsFileTypeId equals to UPDATED_LEASSETS_FILE_TYPE_ID
        defaultLeassetsFileUploadShouldNotBeFound("leassetsFileTypeId.equals=" + UPDATED_LEASSETS_FILE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByLeassetsFileTypeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where leassetsFileTypeId not equals to DEFAULT_LEASSETS_FILE_TYPE_ID
        defaultLeassetsFileUploadShouldNotBeFound("leassetsFileTypeId.notEquals=" + DEFAULT_LEASSETS_FILE_TYPE_ID);

        // Get all the leassetsFileUploadList where leassetsFileTypeId not equals to UPDATED_LEASSETS_FILE_TYPE_ID
        defaultLeassetsFileUploadShouldBeFound("leassetsFileTypeId.notEquals=" + UPDATED_LEASSETS_FILE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByLeassetsFileTypeIdIsInShouldWork() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where leassetsFileTypeId in DEFAULT_LEASSETS_FILE_TYPE_ID or UPDATED_LEASSETS_FILE_TYPE_ID
        defaultLeassetsFileUploadShouldBeFound("leassetsFileTypeId.in=" + DEFAULT_LEASSETS_FILE_TYPE_ID + "," + UPDATED_LEASSETS_FILE_TYPE_ID);

        // Get all the leassetsFileUploadList where leassetsFileTypeId equals to UPDATED_LEASSETS_FILE_TYPE_ID
        defaultLeassetsFileUploadShouldNotBeFound("leassetsFileTypeId.in=" + UPDATED_LEASSETS_FILE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByLeassetsFileTypeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where leassetsFileTypeId is not null
        defaultLeassetsFileUploadShouldBeFound("leassetsFileTypeId.specified=true");

        // Get all the leassetsFileUploadList where leassetsFileTypeId is null
        defaultLeassetsFileUploadShouldNotBeFound("leassetsFileTypeId.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByLeassetsFileTypeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where leassetsFileTypeId is greater than or equal to DEFAULT_LEASSETS_FILE_TYPE_ID
        defaultLeassetsFileUploadShouldBeFound("leassetsFileTypeId.greaterThanOrEqual=" + DEFAULT_LEASSETS_FILE_TYPE_ID);

        // Get all the leassetsFileUploadList where leassetsFileTypeId is greater than or equal to UPDATED_LEASSETS_FILE_TYPE_ID
        defaultLeassetsFileUploadShouldNotBeFound("leassetsFileTypeId.greaterThanOrEqual=" + UPDATED_LEASSETS_FILE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByLeassetsFileTypeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where leassetsFileTypeId is less than or equal to DEFAULT_LEASSETS_FILE_TYPE_ID
        defaultLeassetsFileUploadShouldBeFound("leassetsFileTypeId.lessThanOrEqual=" + DEFAULT_LEASSETS_FILE_TYPE_ID);

        // Get all the leassetsFileUploadList where leassetsFileTypeId is less than or equal to SMALLER_LEASSETS_FILE_TYPE_ID
        defaultLeassetsFileUploadShouldNotBeFound("leassetsFileTypeId.lessThanOrEqual=" + SMALLER_LEASSETS_FILE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByLeassetsFileTypeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where leassetsFileTypeId is less than DEFAULT_LEASSETS_FILE_TYPE_ID
        defaultLeassetsFileUploadShouldNotBeFound("leassetsFileTypeId.lessThan=" + DEFAULT_LEASSETS_FILE_TYPE_ID);

        // Get all the leassetsFileUploadList where leassetsFileTypeId is less than UPDATED_LEASSETS_FILE_TYPE_ID
        defaultLeassetsFileUploadShouldBeFound("leassetsFileTypeId.lessThan=" + UPDATED_LEASSETS_FILE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByLeassetsFileTypeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where leassetsFileTypeId is greater than DEFAULT_LEASSETS_FILE_TYPE_ID
        defaultLeassetsFileUploadShouldNotBeFound("leassetsFileTypeId.greaterThan=" + DEFAULT_LEASSETS_FILE_TYPE_ID);

        // Get all the leassetsFileUploadList where leassetsFileTypeId is greater than SMALLER_LEASSETS_FILE_TYPE_ID
        defaultLeassetsFileUploadShouldBeFound("leassetsFileTypeId.greaterThan=" + SMALLER_LEASSETS_FILE_TYPE_ID);
    }


    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByUploadSuccessfulIsEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where uploadSuccessful equals to DEFAULT_UPLOAD_SUCCESSFUL
        defaultLeassetsFileUploadShouldBeFound("uploadSuccessful.equals=" + DEFAULT_UPLOAD_SUCCESSFUL);

        // Get all the leassetsFileUploadList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultLeassetsFileUploadShouldNotBeFound("uploadSuccessful.equals=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByUploadSuccessfulIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where uploadSuccessful not equals to DEFAULT_UPLOAD_SUCCESSFUL
        defaultLeassetsFileUploadShouldNotBeFound("uploadSuccessful.notEquals=" + DEFAULT_UPLOAD_SUCCESSFUL);

        // Get all the leassetsFileUploadList where uploadSuccessful not equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultLeassetsFileUploadShouldBeFound("uploadSuccessful.notEquals=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByUploadSuccessfulIsInShouldWork() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where uploadSuccessful in DEFAULT_UPLOAD_SUCCESSFUL or UPDATED_UPLOAD_SUCCESSFUL
        defaultLeassetsFileUploadShouldBeFound("uploadSuccessful.in=" + DEFAULT_UPLOAD_SUCCESSFUL + "," + UPDATED_UPLOAD_SUCCESSFUL);

        // Get all the leassetsFileUploadList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultLeassetsFileUploadShouldNotBeFound("uploadSuccessful.in=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByUploadSuccessfulIsNullOrNotNull() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where uploadSuccessful is not null
        defaultLeassetsFileUploadShouldBeFound("uploadSuccessful.specified=true");

        // Get all the leassetsFileUploadList where uploadSuccessful is null
        defaultLeassetsFileUploadShouldNotBeFound("uploadSuccessful.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByUploadProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where uploadProcessed equals to DEFAULT_UPLOAD_PROCESSED
        defaultLeassetsFileUploadShouldBeFound("uploadProcessed.equals=" + DEFAULT_UPLOAD_PROCESSED);

        // Get all the leassetsFileUploadList where uploadProcessed equals to UPDATED_UPLOAD_PROCESSED
        defaultLeassetsFileUploadShouldNotBeFound("uploadProcessed.equals=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByUploadProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where uploadProcessed not equals to DEFAULT_UPLOAD_PROCESSED
        defaultLeassetsFileUploadShouldNotBeFound("uploadProcessed.notEquals=" + DEFAULT_UPLOAD_PROCESSED);

        // Get all the leassetsFileUploadList where uploadProcessed not equals to UPDATED_UPLOAD_PROCESSED
        defaultLeassetsFileUploadShouldBeFound("uploadProcessed.notEquals=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByUploadProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where uploadProcessed in DEFAULT_UPLOAD_PROCESSED or UPDATED_UPLOAD_PROCESSED
        defaultLeassetsFileUploadShouldBeFound("uploadProcessed.in=" + DEFAULT_UPLOAD_PROCESSED + "," + UPDATED_UPLOAD_PROCESSED);

        // Get all the leassetsFileUploadList where uploadProcessed equals to UPDATED_UPLOAD_PROCESSED
        defaultLeassetsFileUploadShouldNotBeFound("uploadProcessed.in=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByUploadProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where uploadProcessed is not null
        defaultLeassetsFileUploadShouldBeFound("uploadProcessed.specified=true");

        // Get all the leassetsFileUploadList where uploadProcessed is null
        defaultLeassetsFileUploadShouldNotBeFound("uploadProcessed.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByUploadTokenIsEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where uploadToken equals to DEFAULT_UPLOAD_TOKEN
        defaultLeassetsFileUploadShouldBeFound("uploadToken.equals=" + DEFAULT_UPLOAD_TOKEN);

        // Get all the leassetsFileUploadList where uploadToken equals to UPDATED_UPLOAD_TOKEN
        defaultLeassetsFileUploadShouldNotBeFound("uploadToken.equals=" + UPDATED_UPLOAD_TOKEN);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByUploadTokenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where uploadToken not equals to DEFAULT_UPLOAD_TOKEN
        defaultLeassetsFileUploadShouldNotBeFound("uploadToken.notEquals=" + DEFAULT_UPLOAD_TOKEN);

        // Get all the leassetsFileUploadList where uploadToken not equals to UPDATED_UPLOAD_TOKEN
        defaultLeassetsFileUploadShouldBeFound("uploadToken.notEquals=" + UPDATED_UPLOAD_TOKEN);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByUploadTokenIsInShouldWork() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where uploadToken in DEFAULT_UPLOAD_TOKEN or UPDATED_UPLOAD_TOKEN
        defaultLeassetsFileUploadShouldBeFound("uploadToken.in=" + DEFAULT_UPLOAD_TOKEN + "," + UPDATED_UPLOAD_TOKEN);

        // Get all the leassetsFileUploadList where uploadToken equals to UPDATED_UPLOAD_TOKEN
        defaultLeassetsFileUploadShouldNotBeFound("uploadToken.in=" + UPDATED_UPLOAD_TOKEN);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByUploadTokenIsNullOrNotNull() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where uploadToken is not null
        defaultLeassetsFileUploadShouldBeFound("uploadToken.specified=true");

        // Get all the leassetsFileUploadList where uploadToken is null
        defaultLeassetsFileUploadShouldNotBeFound("uploadToken.specified=false");
    }
                @Test
    @Transactional
    public void getAllLeassetsFileUploadsByUploadTokenContainsSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where uploadToken contains DEFAULT_UPLOAD_TOKEN
        defaultLeassetsFileUploadShouldBeFound("uploadToken.contains=" + DEFAULT_UPLOAD_TOKEN);

        // Get all the leassetsFileUploadList where uploadToken contains UPDATED_UPLOAD_TOKEN
        defaultLeassetsFileUploadShouldNotBeFound("uploadToken.contains=" + UPDATED_UPLOAD_TOKEN);
    }

    @Test
    @Transactional
    public void getAllLeassetsFileUploadsByUploadTokenNotContainsSomething() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        // Get all the leassetsFileUploadList where uploadToken does not contain DEFAULT_UPLOAD_TOKEN
        defaultLeassetsFileUploadShouldNotBeFound("uploadToken.doesNotContain=" + DEFAULT_UPLOAD_TOKEN);

        // Get all the leassetsFileUploadList where uploadToken does not contain UPDATED_UPLOAD_TOKEN
        defaultLeassetsFileUploadShouldBeFound("uploadToken.doesNotContain=" + UPDATED_UPLOAD_TOKEN);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeassetsFileUploadShouldBeFound(String filter) throws Exception {
        restLeassetsFileUploadMockMvc.perform(get("/api/leassets-file-uploads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leassetsFileUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].leassetsFileTypeId").value(hasItem(DEFAULT_LEASSETS_FILE_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].dataFileContentType").value(hasItem(DEFAULT_DATA_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadToken").value(hasItem(DEFAULT_UPLOAD_TOKEN)));

        // Check, that the count call also returns 1
        restLeassetsFileUploadMockMvc.perform(get("/api/leassets-file-uploads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeassetsFileUploadShouldNotBeFound(String filter) throws Exception {
        restLeassetsFileUploadMockMvc.perform(get("/api/leassets-file-uploads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeassetsFileUploadMockMvc.perform(get("/api/leassets-file-uploads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLeassetsFileUpload() throws Exception {
        // Get the leassetsFileUpload
        restLeassetsFileUploadMockMvc.perform(get("/api/leassets-file-uploads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeassetsFileUpload() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        int databaseSizeBeforeUpdate = leassetsFileUploadRepository.findAll().size();

        // Update the leassetsFileUpload
        LeassetsFileUpload updatedLeassetsFileUpload = leassetsFileUploadRepository.findById(leassetsFileUpload.getId()).get();
        // Disconnect from session so that the updates on updatedLeassetsFileUpload are not directly saved in db
        em.detach(updatedLeassetsFileUpload);
        updatedLeassetsFileUpload
            .description(UPDATED_DESCRIPTION)
            .fileName(UPDATED_FILE_NAME)
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .leassetsFileTypeId(UPDATED_LEASSETS_FILE_TYPE_ID)
            .dataFile(UPDATED_DATA_FILE)
            .dataFileContentType(UPDATED_DATA_FILE_CONTENT_TYPE)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL)
            .uploadProcessed(UPDATED_UPLOAD_PROCESSED)
            .uploadToken(UPDATED_UPLOAD_TOKEN);
        LeassetsFileUploadDTO leassetsFileUploadDTO = leassetsFileUploadMapper.toDto(updatedLeassetsFileUpload);

        restLeassetsFileUploadMockMvc.perform(put("/api/leassets-file-uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leassetsFileUploadDTO)))
            .andExpect(status().isOk());

        // Validate the LeassetsFileUpload in the database
        List<LeassetsFileUpload> leassetsFileUploadList = leassetsFileUploadRepository.findAll();
        assertThat(leassetsFileUploadList).hasSize(databaseSizeBeforeUpdate);
        LeassetsFileUpload testLeassetsFileUpload = leassetsFileUploadList.get(leassetsFileUploadList.size() - 1);
        assertThat(testLeassetsFileUpload.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLeassetsFileUpload.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testLeassetsFileUpload.getPeriodFrom()).isEqualTo(UPDATED_PERIOD_FROM);
        assertThat(testLeassetsFileUpload.getPeriodTo()).isEqualTo(UPDATED_PERIOD_TO);
        assertThat(testLeassetsFileUpload.getLeassetsFileTypeId()).isEqualTo(UPDATED_LEASSETS_FILE_TYPE_ID);
        assertThat(testLeassetsFileUpload.getDataFile()).isEqualTo(UPDATED_DATA_FILE);
        assertThat(testLeassetsFileUpload.getDataFileContentType()).isEqualTo(UPDATED_DATA_FILE_CONTENT_TYPE);
        assertThat(testLeassetsFileUpload.isUploadSuccessful()).isEqualTo(UPDATED_UPLOAD_SUCCESSFUL);
        assertThat(testLeassetsFileUpload.isUploadProcessed()).isEqualTo(UPDATED_UPLOAD_PROCESSED);
        assertThat(testLeassetsFileUpload.getUploadToken()).isEqualTo(UPDATED_UPLOAD_TOKEN);

        // Validate the LeassetsFileUpload in Elasticsearch
        verify(mockLeassetsFileUploadSearchRepository, times(1)).save(testLeassetsFileUpload);
    }

    @Test
    @Transactional
    public void updateNonExistingLeassetsFileUpload() throws Exception {
        int databaseSizeBeforeUpdate = leassetsFileUploadRepository.findAll().size();

        // Create the LeassetsFileUpload
        LeassetsFileUploadDTO leassetsFileUploadDTO = leassetsFileUploadMapper.toDto(leassetsFileUpload);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeassetsFileUploadMockMvc.perform(put("/api/leassets-file-uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leassetsFileUploadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeassetsFileUpload in the database
        List<LeassetsFileUpload> leassetsFileUploadList = leassetsFileUploadRepository.findAll();
        assertThat(leassetsFileUploadList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LeassetsFileUpload in Elasticsearch
        verify(mockLeassetsFileUploadSearchRepository, times(0)).save(leassetsFileUpload);
    }

    @Test
    @Transactional
    public void deleteLeassetsFileUpload() throws Exception {
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);

        int databaseSizeBeforeDelete = leassetsFileUploadRepository.findAll().size();

        // Delete the leassetsFileUpload
        restLeassetsFileUploadMockMvc.perform(delete("/api/leassets-file-uploads/{id}", leassetsFileUpload.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeassetsFileUpload> leassetsFileUploadList = leassetsFileUploadRepository.findAll();
        assertThat(leassetsFileUploadList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LeassetsFileUpload in Elasticsearch
        verify(mockLeassetsFileUploadSearchRepository, times(1)).deleteById(leassetsFileUpload.getId());
    }

    @Test
    @Transactional
    public void searchLeassetsFileUpload() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        leassetsFileUploadRepository.saveAndFlush(leassetsFileUpload);
        when(mockLeassetsFileUploadSearchRepository.search(queryStringQuery("id:" + leassetsFileUpload.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(leassetsFileUpload), PageRequest.of(0, 1), 1));

        // Search the leassetsFileUpload
        restLeassetsFileUploadMockMvc.perform(get("/api/_search/leassets-file-uploads?query=id:" + leassetsFileUpload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leassetsFileUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].leassetsFileTypeId").value(hasItem(DEFAULT_LEASSETS_FILE_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].dataFileContentType").value(hasItem(DEFAULT_DATA_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadToken").value(hasItem(DEFAULT_UPLOAD_TOKEN)));
    }
}
