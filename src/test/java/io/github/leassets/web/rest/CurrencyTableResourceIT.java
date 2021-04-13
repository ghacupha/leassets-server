package io.github.leassets.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.leassets.IntegrationTest;
import io.github.leassets.domain.CurrencyTable;
import io.github.leassets.domain.enumeration.CurrencyLocality;
import io.github.leassets.repository.CurrencyTableRepository;
import io.github.leassets.repository.search.CurrencyTableSearchRepository;
import io.github.leassets.service.criteria.CurrencyTableCriteria;
import io.github.leassets.service.dto.CurrencyTableDTO;
import io.github.leassets.service.mapper.CurrencyTableMapper;
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

/**
 * Integration tests for the {@link CurrencyTableResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CurrencyTableResourceIT {

    private static final String DEFAULT_CURRENCY_CODE = "AAA";
    private static final String UPDATED_CURRENCY_CODE = "BBB";

    private static final CurrencyLocality DEFAULT_LOCALITY = CurrencyLocality.LOCAL;
    private static final CurrencyLocality UPDATED_LOCALITY = CurrencyLocality.FOREIGN;

    private static final String DEFAULT_CURRENCY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/currency-tables";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/currency-tables";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CurrencyTableRepository currencyTableRepository;

    @Autowired
    private CurrencyTableMapper currencyTableMapper;

    /**
     * This repository is mocked in the io.github.leassets.repository.search test package.
     *
     * @see io.github.leassets.repository.search.CurrencyTableSearchRepositoryMockConfiguration
     */
    @Autowired
    private CurrencyTableSearchRepository mockCurrencyTableSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCurrencyTableMockMvc;

    private CurrencyTable currencyTable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurrencyTable createEntity(EntityManager em) {
        CurrencyTable currencyTable = new CurrencyTable()
            .currencyCode(DEFAULT_CURRENCY_CODE)
            .locality(DEFAULT_LOCALITY)
            .currencyName(DEFAULT_CURRENCY_NAME)
            .country(DEFAULT_COUNTRY);
        return currencyTable;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurrencyTable createUpdatedEntity(EntityManager em) {
        CurrencyTable currencyTable = new CurrencyTable()
            .currencyCode(UPDATED_CURRENCY_CODE)
            .locality(UPDATED_LOCALITY)
            .currencyName(UPDATED_CURRENCY_NAME)
            .country(UPDATED_COUNTRY);
        return currencyTable;
    }

    @BeforeEach
    public void initTest() {
        currencyTable = createEntity(em);
    }

    @Test
    @Transactional
    void createCurrencyTable() throws Exception {
        int databaseSizeBeforeCreate = currencyTableRepository.findAll().size();
        // Create the CurrencyTable
        CurrencyTableDTO currencyTableDTO = currencyTableMapper.toDto(currencyTable);
        restCurrencyTableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currencyTableDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CurrencyTable in the database
        List<CurrencyTable> currencyTableList = currencyTableRepository.findAll();
        assertThat(currencyTableList).hasSize(databaseSizeBeforeCreate + 1);
        CurrencyTable testCurrencyTable = currencyTableList.get(currencyTableList.size() - 1);
        assertThat(testCurrencyTable.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
        assertThat(testCurrencyTable.getLocality()).isEqualTo(DEFAULT_LOCALITY);
        assertThat(testCurrencyTable.getCurrencyName()).isEqualTo(DEFAULT_CURRENCY_NAME);
        assertThat(testCurrencyTable.getCountry()).isEqualTo(DEFAULT_COUNTRY);

        // Validate the CurrencyTable in Elasticsearch
        verify(mockCurrencyTableSearchRepository, times(1)).save(testCurrencyTable);
    }

    @Test
    @Transactional
    void createCurrencyTableWithExistingId() throws Exception {
        // Create the CurrencyTable with an existing ID
        currencyTable.setId(1L);
        CurrencyTableDTO currencyTableDTO = currencyTableMapper.toDto(currencyTable);

        int databaseSizeBeforeCreate = currencyTableRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrencyTableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currencyTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrencyTable in the database
        List<CurrencyTable> currencyTableList = currencyTableRepository.findAll();
        assertThat(currencyTableList).hasSize(databaseSizeBeforeCreate);

        // Validate the CurrencyTable in Elasticsearch
        verify(mockCurrencyTableSearchRepository, times(0)).save(currencyTable);
    }

    @Test
    @Transactional
    void checkLocalityIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyTableRepository.findAll().size();
        // set the field null
        currencyTable.setLocality(null);

        // Create the CurrencyTable, which fails.
        CurrencyTableDTO currencyTableDTO = currencyTableMapper.toDto(currencyTable);

        restCurrencyTableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currencyTableDTO))
            )
            .andExpect(status().isBadRequest());

        List<CurrencyTable> currencyTableList = currencyTableRepository.findAll();
        assertThat(currencyTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCurrencyTables() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList
        restCurrencyTableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currencyTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE)))
            .andExpect(jsonPath("$.[*].locality").value(hasItem(DEFAULT_LOCALITY.toString())))
            .andExpect(jsonPath("$.[*].currencyName").value(hasItem(DEFAULT_CURRENCY_NAME)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)));
    }

    @Test
    @Transactional
    void getCurrencyTable() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get the currencyTable
        restCurrencyTableMockMvc
            .perform(get(ENTITY_API_URL_ID, currencyTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(currencyTable.getId().intValue()))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE))
            .andExpect(jsonPath("$.locality").value(DEFAULT_LOCALITY.toString()))
            .andExpect(jsonPath("$.currencyName").value(DEFAULT_CURRENCY_NAME))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY));
    }

    @Test
    @Transactional
    void getCurrencyTablesByIdFiltering() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        Long id = currencyTable.getId();

        defaultCurrencyTableShouldBeFound("id.equals=" + id);
        defaultCurrencyTableShouldNotBeFound("id.notEquals=" + id);

        defaultCurrencyTableShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCurrencyTableShouldNotBeFound("id.greaterThan=" + id);

        defaultCurrencyTableShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCurrencyTableShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByCurrencyCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where currencyCode equals to DEFAULT_CURRENCY_CODE
        defaultCurrencyTableShouldBeFound("currencyCode.equals=" + DEFAULT_CURRENCY_CODE);

        // Get all the currencyTableList where currencyCode equals to UPDATED_CURRENCY_CODE
        defaultCurrencyTableShouldNotBeFound("currencyCode.equals=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByCurrencyCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where currencyCode not equals to DEFAULT_CURRENCY_CODE
        defaultCurrencyTableShouldNotBeFound("currencyCode.notEquals=" + DEFAULT_CURRENCY_CODE);

        // Get all the currencyTableList where currencyCode not equals to UPDATED_CURRENCY_CODE
        defaultCurrencyTableShouldBeFound("currencyCode.notEquals=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByCurrencyCodeIsInShouldWork() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where currencyCode in DEFAULT_CURRENCY_CODE or UPDATED_CURRENCY_CODE
        defaultCurrencyTableShouldBeFound("currencyCode.in=" + DEFAULT_CURRENCY_CODE + "," + UPDATED_CURRENCY_CODE);

        // Get all the currencyTableList where currencyCode equals to UPDATED_CURRENCY_CODE
        defaultCurrencyTableShouldNotBeFound("currencyCode.in=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByCurrencyCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where currencyCode is not null
        defaultCurrencyTableShouldBeFound("currencyCode.specified=true");

        // Get all the currencyTableList where currencyCode is null
        defaultCurrencyTableShouldNotBeFound("currencyCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByCurrencyCodeContainsSomething() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where currencyCode contains DEFAULT_CURRENCY_CODE
        defaultCurrencyTableShouldBeFound("currencyCode.contains=" + DEFAULT_CURRENCY_CODE);

        // Get all the currencyTableList where currencyCode contains UPDATED_CURRENCY_CODE
        defaultCurrencyTableShouldNotBeFound("currencyCode.contains=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByCurrencyCodeNotContainsSomething() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where currencyCode does not contain DEFAULT_CURRENCY_CODE
        defaultCurrencyTableShouldNotBeFound("currencyCode.doesNotContain=" + DEFAULT_CURRENCY_CODE);

        // Get all the currencyTableList where currencyCode does not contain UPDATED_CURRENCY_CODE
        defaultCurrencyTableShouldBeFound("currencyCode.doesNotContain=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByLocalityIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where locality equals to DEFAULT_LOCALITY
        defaultCurrencyTableShouldBeFound("locality.equals=" + DEFAULT_LOCALITY);

        // Get all the currencyTableList where locality equals to UPDATED_LOCALITY
        defaultCurrencyTableShouldNotBeFound("locality.equals=" + UPDATED_LOCALITY);
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByLocalityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where locality not equals to DEFAULT_LOCALITY
        defaultCurrencyTableShouldNotBeFound("locality.notEquals=" + DEFAULT_LOCALITY);

        // Get all the currencyTableList where locality not equals to UPDATED_LOCALITY
        defaultCurrencyTableShouldBeFound("locality.notEquals=" + UPDATED_LOCALITY);
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByLocalityIsInShouldWork() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where locality in DEFAULT_LOCALITY or UPDATED_LOCALITY
        defaultCurrencyTableShouldBeFound("locality.in=" + DEFAULT_LOCALITY + "," + UPDATED_LOCALITY);

        // Get all the currencyTableList where locality equals to UPDATED_LOCALITY
        defaultCurrencyTableShouldNotBeFound("locality.in=" + UPDATED_LOCALITY);
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByLocalityIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where locality is not null
        defaultCurrencyTableShouldBeFound("locality.specified=true");

        // Get all the currencyTableList where locality is null
        defaultCurrencyTableShouldNotBeFound("locality.specified=false");
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByCurrencyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where currencyName equals to DEFAULT_CURRENCY_NAME
        defaultCurrencyTableShouldBeFound("currencyName.equals=" + DEFAULT_CURRENCY_NAME);

        // Get all the currencyTableList where currencyName equals to UPDATED_CURRENCY_NAME
        defaultCurrencyTableShouldNotBeFound("currencyName.equals=" + UPDATED_CURRENCY_NAME);
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByCurrencyNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where currencyName not equals to DEFAULT_CURRENCY_NAME
        defaultCurrencyTableShouldNotBeFound("currencyName.notEquals=" + DEFAULT_CURRENCY_NAME);

        // Get all the currencyTableList where currencyName not equals to UPDATED_CURRENCY_NAME
        defaultCurrencyTableShouldBeFound("currencyName.notEquals=" + UPDATED_CURRENCY_NAME);
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByCurrencyNameIsInShouldWork() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where currencyName in DEFAULT_CURRENCY_NAME or UPDATED_CURRENCY_NAME
        defaultCurrencyTableShouldBeFound("currencyName.in=" + DEFAULT_CURRENCY_NAME + "," + UPDATED_CURRENCY_NAME);

        // Get all the currencyTableList where currencyName equals to UPDATED_CURRENCY_NAME
        defaultCurrencyTableShouldNotBeFound("currencyName.in=" + UPDATED_CURRENCY_NAME);
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByCurrencyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where currencyName is not null
        defaultCurrencyTableShouldBeFound("currencyName.specified=true");

        // Get all the currencyTableList where currencyName is null
        defaultCurrencyTableShouldNotBeFound("currencyName.specified=false");
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByCurrencyNameContainsSomething() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where currencyName contains DEFAULT_CURRENCY_NAME
        defaultCurrencyTableShouldBeFound("currencyName.contains=" + DEFAULT_CURRENCY_NAME);

        // Get all the currencyTableList where currencyName contains UPDATED_CURRENCY_NAME
        defaultCurrencyTableShouldNotBeFound("currencyName.contains=" + UPDATED_CURRENCY_NAME);
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByCurrencyNameNotContainsSomething() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where currencyName does not contain DEFAULT_CURRENCY_NAME
        defaultCurrencyTableShouldNotBeFound("currencyName.doesNotContain=" + DEFAULT_CURRENCY_NAME);

        // Get all the currencyTableList where currencyName does not contain UPDATED_CURRENCY_NAME
        defaultCurrencyTableShouldBeFound("currencyName.doesNotContain=" + UPDATED_CURRENCY_NAME);
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where country equals to DEFAULT_COUNTRY
        defaultCurrencyTableShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the currencyTableList where country equals to UPDATED_COUNTRY
        defaultCurrencyTableShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByCountryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where country not equals to DEFAULT_COUNTRY
        defaultCurrencyTableShouldNotBeFound("country.notEquals=" + DEFAULT_COUNTRY);

        // Get all the currencyTableList where country not equals to UPDATED_COUNTRY
        defaultCurrencyTableShouldBeFound("country.notEquals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultCurrencyTableShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the currencyTableList where country equals to UPDATED_COUNTRY
        defaultCurrencyTableShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where country is not null
        defaultCurrencyTableShouldBeFound("country.specified=true");

        // Get all the currencyTableList where country is null
        defaultCurrencyTableShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByCountryContainsSomething() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where country contains DEFAULT_COUNTRY
        defaultCurrencyTableShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the currencyTableList where country contains UPDATED_COUNTRY
        defaultCurrencyTableShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllCurrencyTablesByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        // Get all the currencyTableList where country does not contain DEFAULT_COUNTRY
        defaultCurrencyTableShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the currencyTableList where country does not contain UPDATED_COUNTRY
        defaultCurrencyTableShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCurrencyTableShouldBeFound(String filter) throws Exception {
        restCurrencyTableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currencyTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE)))
            .andExpect(jsonPath("$.[*].locality").value(hasItem(DEFAULT_LOCALITY.toString())))
            .andExpect(jsonPath("$.[*].currencyName").value(hasItem(DEFAULT_CURRENCY_NAME)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)));

        // Check, that the count call also returns 1
        restCurrencyTableMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCurrencyTableShouldNotBeFound(String filter) throws Exception {
        restCurrencyTableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCurrencyTableMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCurrencyTable() throws Exception {
        // Get the currencyTable
        restCurrencyTableMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCurrencyTable() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        int databaseSizeBeforeUpdate = currencyTableRepository.findAll().size();

        // Update the currencyTable
        CurrencyTable updatedCurrencyTable = currencyTableRepository.findById(currencyTable.getId()).get();
        // Disconnect from session so that the updates on updatedCurrencyTable are not directly saved in db
        em.detach(updatedCurrencyTable);
        updatedCurrencyTable
            .currencyCode(UPDATED_CURRENCY_CODE)
            .locality(UPDATED_LOCALITY)
            .currencyName(UPDATED_CURRENCY_NAME)
            .country(UPDATED_COUNTRY);
        CurrencyTableDTO currencyTableDTO = currencyTableMapper.toDto(updatedCurrencyTable);

        restCurrencyTableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, currencyTableDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currencyTableDTO))
            )
            .andExpect(status().isOk());

        // Validate the CurrencyTable in the database
        List<CurrencyTable> currencyTableList = currencyTableRepository.findAll();
        assertThat(currencyTableList).hasSize(databaseSizeBeforeUpdate);
        CurrencyTable testCurrencyTable = currencyTableList.get(currencyTableList.size() - 1);
        assertThat(testCurrencyTable.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testCurrencyTable.getLocality()).isEqualTo(UPDATED_LOCALITY);
        assertThat(testCurrencyTable.getCurrencyName()).isEqualTo(UPDATED_CURRENCY_NAME);
        assertThat(testCurrencyTable.getCountry()).isEqualTo(UPDATED_COUNTRY);

        // Validate the CurrencyTable in Elasticsearch
        verify(mockCurrencyTableSearchRepository).save(testCurrencyTable);
    }

    @Test
    @Transactional
    void putNonExistingCurrencyTable() throws Exception {
        int databaseSizeBeforeUpdate = currencyTableRepository.findAll().size();
        currencyTable.setId(count.incrementAndGet());

        // Create the CurrencyTable
        CurrencyTableDTO currencyTableDTO = currencyTableMapper.toDto(currencyTable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrencyTableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, currencyTableDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currencyTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrencyTable in the database
        List<CurrencyTable> currencyTableList = currencyTableRepository.findAll();
        assertThat(currencyTableList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CurrencyTable in Elasticsearch
        verify(mockCurrencyTableSearchRepository, times(0)).save(currencyTable);
    }

    @Test
    @Transactional
    void putWithIdMismatchCurrencyTable() throws Exception {
        int databaseSizeBeforeUpdate = currencyTableRepository.findAll().size();
        currencyTable.setId(count.incrementAndGet());

        // Create the CurrencyTable
        CurrencyTableDTO currencyTableDTO = currencyTableMapper.toDto(currencyTable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrencyTableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currencyTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrencyTable in the database
        List<CurrencyTable> currencyTableList = currencyTableRepository.findAll();
        assertThat(currencyTableList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CurrencyTable in Elasticsearch
        verify(mockCurrencyTableSearchRepository, times(0)).save(currencyTable);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCurrencyTable() throws Exception {
        int databaseSizeBeforeUpdate = currencyTableRepository.findAll().size();
        currencyTable.setId(count.incrementAndGet());

        // Create the CurrencyTable
        CurrencyTableDTO currencyTableDTO = currencyTableMapper.toDto(currencyTable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrencyTableMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currencyTableDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CurrencyTable in the database
        List<CurrencyTable> currencyTableList = currencyTableRepository.findAll();
        assertThat(currencyTableList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CurrencyTable in Elasticsearch
        verify(mockCurrencyTableSearchRepository, times(0)).save(currencyTable);
    }

    @Test
    @Transactional
    void partialUpdateCurrencyTableWithPatch() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        int databaseSizeBeforeUpdate = currencyTableRepository.findAll().size();

        // Update the currencyTable using partial update
        CurrencyTable partialUpdatedCurrencyTable = new CurrencyTable();
        partialUpdatedCurrencyTable.setId(currencyTable.getId());

        partialUpdatedCurrencyTable.currencyCode(UPDATED_CURRENCY_CODE).locality(UPDATED_LOCALITY).country(UPDATED_COUNTRY);

        restCurrencyTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCurrencyTable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCurrencyTable))
            )
            .andExpect(status().isOk());

        // Validate the CurrencyTable in the database
        List<CurrencyTable> currencyTableList = currencyTableRepository.findAll();
        assertThat(currencyTableList).hasSize(databaseSizeBeforeUpdate);
        CurrencyTable testCurrencyTable = currencyTableList.get(currencyTableList.size() - 1);
        assertThat(testCurrencyTable.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testCurrencyTable.getLocality()).isEqualTo(UPDATED_LOCALITY);
        assertThat(testCurrencyTable.getCurrencyName()).isEqualTo(DEFAULT_CURRENCY_NAME);
        assertThat(testCurrencyTable.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void fullUpdateCurrencyTableWithPatch() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        int databaseSizeBeforeUpdate = currencyTableRepository.findAll().size();

        // Update the currencyTable using partial update
        CurrencyTable partialUpdatedCurrencyTable = new CurrencyTable();
        partialUpdatedCurrencyTable.setId(currencyTable.getId());

        partialUpdatedCurrencyTable
            .currencyCode(UPDATED_CURRENCY_CODE)
            .locality(UPDATED_LOCALITY)
            .currencyName(UPDATED_CURRENCY_NAME)
            .country(UPDATED_COUNTRY);

        restCurrencyTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCurrencyTable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCurrencyTable))
            )
            .andExpect(status().isOk());

        // Validate the CurrencyTable in the database
        List<CurrencyTable> currencyTableList = currencyTableRepository.findAll();
        assertThat(currencyTableList).hasSize(databaseSizeBeforeUpdate);
        CurrencyTable testCurrencyTable = currencyTableList.get(currencyTableList.size() - 1);
        assertThat(testCurrencyTable.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testCurrencyTable.getLocality()).isEqualTo(UPDATED_LOCALITY);
        assertThat(testCurrencyTable.getCurrencyName()).isEqualTo(UPDATED_CURRENCY_NAME);
        assertThat(testCurrencyTable.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void patchNonExistingCurrencyTable() throws Exception {
        int databaseSizeBeforeUpdate = currencyTableRepository.findAll().size();
        currencyTable.setId(count.incrementAndGet());

        // Create the CurrencyTable
        CurrencyTableDTO currencyTableDTO = currencyTableMapper.toDto(currencyTable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrencyTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, currencyTableDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currencyTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrencyTable in the database
        List<CurrencyTable> currencyTableList = currencyTableRepository.findAll();
        assertThat(currencyTableList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CurrencyTable in Elasticsearch
        verify(mockCurrencyTableSearchRepository, times(0)).save(currencyTable);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCurrencyTable() throws Exception {
        int databaseSizeBeforeUpdate = currencyTableRepository.findAll().size();
        currencyTable.setId(count.incrementAndGet());

        // Create the CurrencyTable
        CurrencyTableDTO currencyTableDTO = currencyTableMapper.toDto(currencyTable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrencyTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currencyTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrencyTable in the database
        List<CurrencyTable> currencyTableList = currencyTableRepository.findAll();
        assertThat(currencyTableList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CurrencyTable in Elasticsearch
        verify(mockCurrencyTableSearchRepository, times(0)).save(currencyTable);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCurrencyTable() throws Exception {
        int databaseSizeBeforeUpdate = currencyTableRepository.findAll().size();
        currencyTable.setId(count.incrementAndGet());

        // Create the CurrencyTable
        CurrencyTableDTO currencyTableDTO = currencyTableMapper.toDto(currencyTable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrencyTableMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currencyTableDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CurrencyTable in the database
        List<CurrencyTable> currencyTableList = currencyTableRepository.findAll();
        assertThat(currencyTableList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CurrencyTable in Elasticsearch
        verify(mockCurrencyTableSearchRepository, times(0)).save(currencyTable);
    }

    @Test
    @Transactional
    void deleteCurrencyTable() throws Exception {
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);

        int databaseSizeBeforeDelete = currencyTableRepository.findAll().size();

        // Delete the currencyTable
        restCurrencyTableMockMvc
            .perform(delete(ENTITY_API_URL_ID, currencyTable.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CurrencyTable> currencyTableList = currencyTableRepository.findAll();
        assertThat(currencyTableList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CurrencyTable in Elasticsearch
        verify(mockCurrencyTableSearchRepository, times(1)).deleteById(currencyTable.getId());
    }

    @Test
    @Transactional
    void searchCurrencyTable() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        currencyTableRepository.saveAndFlush(currencyTable);
        when(mockCurrencyTableSearchRepository.search(queryStringQuery("id:" + currencyTable.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(currencyTable), PageRequest.of(0, 1), 1));

        // Search the currencyTable
        restCurrencyTableMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + currencyTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currencyTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE)))
            .andExpect(jsonPath("$.[*].locality").value(hasItem(DEFAULT_LOCALITY.toString())))
            .andExpect(jsonPath("$.[*].currencyName").value(hasItem(DEFAULT_CURRENCY_NAME)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)));
    }
}
