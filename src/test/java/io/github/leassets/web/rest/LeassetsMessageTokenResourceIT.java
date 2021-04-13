package io.github.leassets.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.leassets.IntegrationTest;
import io.github.leassets.domain.LeassetsMessageToken;
import io.github.leassets.repository.LeassetsMessageTokenRepository;
import io.github.leassets.repository.search.LeassetsMessageTokenSearchRepository;
import io.github.leassets.service.criteria.LeassetsMessageTokenCriteria;
import io.github.leassets.service.dto.LeassetsMessageTokenDTO;
import io.github.leassets.service.mapper.LeassetsMessageTokenMapper;
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
 * Integration tests for the {@link LeassetsMessageTokenResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LeassetsMessageTokenResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_TIME_SENT = 1L;
    private static final Long UPDATED_TIME_SENT = 2L;
    private static final Long SMALLER_TIME_SENT = 1L - 1L;

    private static final String DEFAULT_TOKEN_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RECEIVED = false;
    private static final Boolean UPDATED_RECEIVED = true;

    private static final Boolean DEFAULT_ACTIONED = false;
    private static final Boolean UPDATED_ACTIONED = true;

    private static final Boolean DEFAULT_CONTENT_FULLY_ENQUEUED = false;
    private static final Boolean UPDATED_CONTENT_FULLY_ENQUEUED = true;

    private static final String ENTITY_API_URL = "/api/leassets-message-tokens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/leassets-message-tokens";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeassetsMessageTokenRepository leassetsMessageTokenRepository;

    @Autowired
    private LeassetsMessageTokenMapper leassetsMessageTokenMapper;

    /**
     * This repository is mocked in the io.github.leassets.repository.search test package.
     *
     * @see io.github.leassets.repository.search.LeassetsMessageTokenSearchRepositoryMockConfiguration
     */
    @Autowired
    private LeassetsMessageTokenSearchRepository mockLeassetsMessageTokenSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeassetsMessageTokenMockMvc;

    private LeassetsMessageToken leassetsMessageToken;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeassetsMessageToken createEntity(EntityManager em) {
        LeassetsMessageToken leassetsMessageToken = new LeassetsMessageToken()
            .description(DEFAULT_DESCRIPTION)
            .timeSent(DEFAULT_TIME_SENT)
            .tokenValue(DEFAULT_TOKEN_VALUE)
            .received(DEFAULT_RECEIVED)
            .actioned(DEFAULT_ACTIONED)
            .contentFullyEnqueued(DEFAULT_CONTENT_FULLY_ENQUEUED);
        return leassetsMessageToken;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeassetsMessageToken createUpdatedEntity(EntityManager em) {
        LeassetsMessageToken leassetsMessageToken = new LeassetsMessageToken()
            .description(UPDATED_DESCRIPTION)
            .timeSent(UPDATED_TIME_SENT)
            .tokenValue(UPDATED_TOKEN_VALUE)
            .received(UPDATED_RECEIVED)
            .actioned(UPDATED_ACTIONED)
            .contentFullyEnqueued(UPDATED_CONTENT_FULLY_ENQUEUED);
        return leassetsMessageToken;
    }

    @BeforeEach
    public void initTest() {
        leassetsMessageToken = createEntity(em);
    }

    @Test
    @Transactional
    void createLeassetsMessageToken() throws Exception {
        int databaseSizeBeforeCreate = leassetsMessageTokenRepository.findAll().size();
        // Create the LeassetsMessageToken
        LeassetsMessageTokenDTO leassetsMessageTokenDTO = leassetsMessageTokenMapper.toDto(leassetsMessageToken);
        restLeassetsMessageTokenMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leassetsMessageTokenDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LeassetsMessageToken in the database
        List<LeassetsMessageToken> leassetsMessageTokenList = leassetsMessageTokenRepository.findAll();
        assertThat(leassetsMessageTokenList).hasSize(databaseSizeBeforeCreate + 1);
        LeassetsMessageToken testLeassetsMessageToken = leassetsMessageTokenList.get(leassetsMessageTokenList.size() - 1);
        assertThat(testLeassetsMessageToken.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLeassetsMessageToken.getTimeSent()).isEqualTo(DEFAULT_TIME_SENT);
        assertThat(testLeassetsMessageToken.getTokenValue()).isEqualTo(DEFAULT_TOKEN_VALUE);
        assertThat(testLeassetsMessageToken.getReceived()).isEqualTo(DEFAULT_RECEIVED);
        assertThat(testLeassetsMessageToken.getActioned()).isEqualTo(DEFAULT_ACTIONED);
        assertThat(testLeassetsMessageToken.getContentFullyEnqueued()).isEqualTo(DEFAULT_CONTENT_FULLY_ENQUEUED);

        // Validate the LeassetsMessageToken in Elasticsearch
        verify(mockLeassetsMessageTokenSearchRepository, times(1)).save(testLeassetsMessageToken);
    }

    @Test
    @Transactional
    void createLeassetsMessageTokenWithExistingId() throws Exception {
        // Create the LeassetsMessageToken with an existing ID
        leassetsMessageToken.setId(1L);
        LeassetsMessageTokenDTO leassetsMessageTokenDTO = leassetsMessageTokenMapper.toDto(leassetsMessageToken);

        int databaseSizeBeforeCreate = leassetsMessageTokenRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeassetsMessageTokenMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leassetsMessageTokenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeassetsMessageToken in the database
        List<LeassetsMessageToken> leassetsMessageTokenList = leassetsMessageTokenRepository.findAll();
        assertThat(leassetsMessageTokenList).hasSize(databaseSizeBeforeCreate);

        // Validate the LeassetsMessageToken in Elasticsearch
        verify(mockLeassetsMessageTokenSearchRepository, times(0)).save(leassetsMessageToken);
    }

    @Test
    @Transactional
    void checkTimeSentIsRequired() throws Exception {
        int databaseSizeBeforeTest = leassetsMessageTokenRepository.findAll().size();
        // set the field null
        leassetsMessageToken.setTimeSent(null);

        // Create the LeassetsMessageToken, which fails.
        LeassetsMessageTokenDTO leassetsMessageTokenDTO = leassetsMessageTokenMapper.toDto(leassetsMessageToken);

        restLeassetsMessageTokenMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leassetsMessageTokenDTO))
            )
            .andExpect(status().isBadRequest());

        List<LeassetsMessageToken> leassetsMessageTokenList = leassetsMessageTokenRepository.findAll();
        assertThat(leassetsMessageTokenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTokenValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = leassetsMessageTokenRepository.findAll().size();
        // set the field null
        leassetsMessageToken.setTokenValue(null);

        // Create the LeassetsMessageToken, which fails.
        LeassetsMessageTokenDTO leassetsMessageTokenDTO = leassetsMessageTokenMapper.toDto(leassetsMessageToken);

        restLeassetsMessageTokenMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leassetsMessageTokenDTO))
            )
            .andExpect(status().isBadRequest());

        List<LeassetsMessageToken> leassetsMessageTokenList = leassetsMessageTokenRepository.findAll();
        assertThat(leassetsMessageTokenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokens() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList
        restLeassetsMessageTokenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leassetsMessageToken.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].timeSent").value(hasItem(DEFAULT_TIME_SENT.intValue())))
            .andExpect(jsonPath("$.[*].tokenValue").value(hasItem(DEFAULT_TOKEN_VALUE)))
            .andExpect(jsonPath("$.[*].received").value(hasItem(DEFAULT_RECEIVED.booleanValue())))
            .andExpect(jsonPath("$.[*].actioned").value(hasItem(DEFAULT_ACTIONED.booleanValue())))
            .andExpect(jsonPath("$.[*].contentFullyEnqueued").value(hasItem(DEFAULT_CONTENT_FULLY_ENQUEUED.booleanValue())));
    }

    @Test
    @Transactional
    void getLeassetsMessageToken() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get the leassetsMessageToken
        restLeassetsMessageTokenMockMvc
            .perform(get(ENTITY_API_URL_ID, leassetsMessageToken.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leassetsMessageToken.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.timeSent").value(DEFAULT_TIME_SENT.intValue()))
            .andExpect(jsonPath("$.tokenValue").value(DEFAULT_TOKEN_VALUE))
            .andExpect(jsonPath("$.received").value(DEFAULT_RECEIVED.booleanValue()))
            .andExpect(jsonPath("$.actioned").value(DEFAULT_ACTIONED.booleanValue()))
            .andExpect(jsonPath("$.contentFullyEnqueued").value(DEFAULT_CONTENT_FULLY_ENQUEUED.booleanValue()));
    }

    @Test
    @Transactional
    void getLeassetsMessageTokensByIdFiltering() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        Long id = leassetsMessageToken.getId();

        defaultLeassetsMessageTokenShouldBeFound("id.equals=" + id);
        defaultLeassetsMessageTokenShouldNotBeFound("id.notEquals=" + id);

        defaultLeassetsMessageTokenShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeassetsMessageTokenShouldNotBeFound("id.greaterThan=" + id);

        defaultLeassetsMessageTokenShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeassetsMessageTokenShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where description equals to DEFAULT_DESCRIPTION
        defaultLeassetsMessageTokenShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the leassetsMessageTokenList where description equals to UPDATED_DESCRIPTION
        defaultLeassetsMessageTokenShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where description not equals to DEFAULT_DESCRIPTION
        defaultLeassetsMessageTokenShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the leassetsMessageTokenList where description not equals to UPDATED_DESCRIPTION
        defaultLeassetsMessageTokenShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultLeassetsMessageTokenShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the leassetsMessageTokenList where description equals to UPDATED_DESCRIPTION
        defaultLeassetsMessageTokenShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where description is not null
        defaultLeassetsMessageTokenShouldBeFound("description.specified=true");

        // Get all the leassetsMessageTokenList where description is null
        defaultLeassetsMessageTokenShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where description contains DEFAULT_DESCRIPTION
        defaultLeassetsMessageTokenShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the leassetsMessageTokenList where description contains UPDATED_DESCRIPTION
        defaultLeassetsMessageTokenShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where description does not contain DEFAULT_DESCRIPTION
        defaultLeassetsMessageTokenShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the leassetsMessageTokenList where description does not contain UPDATED_DESCRIPTION
        defaultLeassetsMessageTokenShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByTimeSentIsEqualToSomething() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where timeSent equals to DEFAULT_TIME_SENT
        defaultLeassetsMessageTokenShouldBeFound("timeSent.equals=" + DEFAULT_TIME_SENT);

        // Get all the leassetsMessageTokenList where timeSent equals to UPDATED_TIME_SENT
        defaultLeassetsMessageTokenShouldNotBeFound("timeSent.equals=" + UPDATED_TIME_SENT);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByTimeSentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where timeSent not equals to DEFAULT_TIME_SENT
        defaultLeassetsMessageTokenShouldNotBeFound("timeSent.notEquals=" + DEFAULT_TIME_SENT);

        // Get all the leassetsMessageTokenList where timeSent not equals to UPDATED_TIME_SENT
        defaultLeassetsMessageTokenShouldBeFound("timeSent.notEquals=" + UPDATED_TIME_SENT);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByTimeSentIsInShouldWork() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where timeSent in DEFAULT_TIME_SENT or UPDATED_TIME_SENT
        defaultLeassetsMessageTokenShouldBeFound("timeSent.in=" + DEFAULT_TIME_SENT + "," + UPDATED_TIME_SENT);

        // Get all the leassetsMessageTokenList where timeSent equals to UPDATED_TIME_SENT
        defaultLeassetsMessageTokenShouldNotBeFound("timeSent.in=" + UPDATED_TIME_SENT);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByTimeSentIsNullOrNotNull() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where timeSent is not null
        defaultLeassetsMessageTokenShouldBeFound("timeSent.specified=true");

        // Get all the leassetsMessageTokenList where timeSent is null
        defaultLeassetsMessageTokenShouldNotBeFound("timeSent.specified=false");
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByTimeSentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where timeSent is greater than or equal to DEFAULT_TIME_SENT
        defaultLeassetsMessageTokenShouldBeFound("timeSent.greaterThanOrEqual=" + DEFAULT_TIME_SENT);

        // Get all the leassetsMessageTokenList where timeSent is greater than or equal to UPDATED_TIME_SENT
        defaultLeassetsMessageTokenShouldNotBeFound("timeSent.greaterThanOrEqual=" + UPDATED_TIME_SENT);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByTimeSentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where timeSent is less than or equal to DEFAULT_TIME_SENT
        defaultLeassetsMessageTokenShouldBeFound("timeSent.lessThanOrEqual=" + DEFAULT_TIME_SENT);

        // Get all the leassetsMessageTokenList where timeSent is less than or equal to SMALLER_TIME_SENT
        defaultLeassetsMessageTokenShouldNotBeFound("timeSent.lessThanOrEqual=" + SMALLER_TIME_SENT);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByTimeSentIsLessThanSomething() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where timeSent is less than DEFAULT_TIME_SENT
        defaultLeassetsMessageTokenShouldNotBeFound("timeSent.lessThan=" + DEFAULT_TIME_SENT);

        // Get all the leassetsMessageTokenList where timeSent is less than UPDATED_TIME_SENT
        defaultLeassetsMessageTokenShouldBeFound("timeSent.lessThan=" + UPDATED_TIME_SENT);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByTimeSentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where timeSent is greater than DEFAULT_TIME_SENT
        defaultLeassetsMessageTokenShouldNotBeFound("timeSent.greaterThan=" + DEFAULT_TIME_SENT);

        // Get all the leassetsMessageTokenList where timeSent is greater than SMALLER_TIME_SENT
        defaultLeassetsMessageTokenShouldBeFound("timeSent.greaterThan=" + SMALLER_TIME_SENT);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByTokenValueIsEqualToSomething() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where tokenValue equals to DEFAULT_TOKEN_VALUE
        defaultLeassetsMessageTokenShouldBeFound("tokenValue.equals=" + DEFAULT_TOKEN_VALUE);

        // Get all the leassetsMessageTokenList where tokenValue equals to UPDATED_TOKEN_VALUE
        defaultLeassetsMessageTokenShouldNotBeFound("tokenValue.equals=" + UPDATED_TOKEN_VALUE);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByTokenValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where tokenValue not equals to DEFAULT_TOKEN_VALUE
        defaultLeassetsMessageTokenShouldNotBeFound("tokenValue.notEquals=" + DEFAULT_TOKEN_VALUE);

        // Get all the leassetsMessageTokenList where tokenValue not equals to UPDATED_TOKEN_VALUE
        defaultLeassetsMessageTokenShouldBeFound("tokenValue.notEquals=" + UPDATED_TOKEN_VALUE);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByTokenValueIsInShouldWork() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where tokenValue in DEFAULT_TOKEN_VALUE or UPDATED_TOKEN_VALUE
        defaultLeassetsMessageTokenShouldBeFound("tokenValue.in=" + DEFAULT_TOKEN_VALUE + "," + UPDATED_TOKEN_VALUE);

        // Get all the leassetsMessageTokenList where tokenValue equals to UPDATED_TOKEN_VALUE
        defaultLeassetsMessageTokenShouldNotBeFound("tokenValue.in=" + UPDATED_TOKEN_VALUE);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByTokenValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where tokenValue is not null
        defaultLeassetsMessageTokenShouldBeFound("tokenValue.specified=true");

        // Get all the leassetsMessageTokenList where tokenValue is null
        defaultLeassetsMessageTokenShouldNotBeFound("tokenValue.specified=false");
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByTokenValueContainsSomething() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where tokenValue contains DEFAULT_TOKEN_VALUE
        defaultLeassetsMessageTokenShouldBeFound("tokenValue.contains=" + DEFAULT_TOKEN_VALUE);

        // Get all the leassetsMessageTokenList where tokenValue contains UPDATED_TOKEN_VALUE
        defaultLeassetsMessageTokenShouldNotBeFound("tokenValue.contains=" + UPDATED_TOKEN_VALUE);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByTokenValueNotContainsSomething() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where tokenValue does not contain DEFAULT_TOKEN_VALUE
        defaultLeassetsMessageTokenShouldNotBeFound("tokenValue.doesNotContain=" + DEFAULT_TOKEN_VALUE);

        // Get all the leassetsMessageTokenList where tokenValue does not contain UPDATED_TOKEN_VALUE
        defaultLeassetsMessageTokenShouldBeFound("tokenValue.doesNotContain=" + UPDATED_TOKEN_VALUE);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByReceivedIsEqualToSomething() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where received equals to DEFAULT_RECEIVED
        defaultLeassetsMessageTokenShouldBeFound("received.equals=" + DEFAULT_RECEIVED);

        // Get all the leassetsMessageTokenList where received equals to UPDATED_RECEIVED
        defaultLeassetsMessageTokenShouldNotBeFound("received.equals=" + UPDATED_RECEIVED);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByReceivedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where received not equals to DEFAULT_RECEIVED
        defaultLeassetsMessageTokenShouldNotBeFound("received.notEquals=" + DEFAULT_RECEIVED);

        // Get all the leassetsMessageTokenList where received not equals to UPDATED_RECEIVED
        defaultLeassetsMessageTokenShouldBeFound("received.notEquals=" + UPDATED_RECEIVED);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByReceivedIsInShouldWork() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where received in DEFAULT_RECEIVED or UPDATED_RECEIVED
        defaultLeassetsMessageTokenShouldBeFound("received.in=" + DEFAULT_RECEIVED + "," + UPDATED_RECEIVED);

        // Get all the leassetsMessageTokenList where received equals to UPDATED_RECEIVED
        defaultLeassetsMessageTokenShouldNotBeFound("received.in=" + UPDATED_RECEIVED);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByReceivedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where received is not null
        defaultLeassetsMessageTokenShouldBeFound("received.specified=true");

        // Get all the leassetsMessageTokenList where received is null
        defaultLeassetsMessageTokenShouldNotBeFound("received.specified=false");
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByActionedIsEqualToSomething() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where actioned equals to DEFAULT_ACTIONED
        defaultLeassetsMessageTokenShouldBeFound("actioned.equals=" + DEFAULT_ACTIONED);

        // Get all the leassetsMessageTokenList where actioned equals to UPDATED_ACTIONED
        defaultLeassetsMessageTokenShouldNotBeFound("actioned.equals=" + UPDATED_ACTIONED);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByActionedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where actioned not equals to DEFAULT_ACTIONED
        defaultLeassetsMessageTokenShouldNotBeFound("actioned.notEquals=" + DEFAULT_ACTIONED);

        // Get all the leassetsMessageTokenList where actioned not equals to UPDATED_ACTIONED
        defaultLeassetsMessageTokenShouldBeFound("actioned.notEquals=" + UPDATED_ACTIONED);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByActionedIsInShouldWork() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where actioned in DEFAULT_ACTIONED or UPDATED_ACTIONED
        defaultLeassetsMessageTokenShouldBeFound("actioned.in=" + DEFAULT_ACTIONED + "," + UPDATED_ACTIONED);

        // Get all the leassetsMessageTokenList where actioned equals to UPDATED_ACTIONED
        defaultLeassetsMessageTokenShouldNotBeFound("actioned.in=" + UPDATED_ACTIONED);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByActionedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where actioned is not null
        defaultLeassetsMessageTokenShouldBeFound("actioned.specified=true");

        // Get all the leassetsMessageTokenList where actioned is null
        defaultLeassetsMessageTokenShouldNotBeFound("actioned.specified=false");
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByContentFullyEnqueuedIsEqualToSomething() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where contentFullyEnqueued equals to DEFAULT_CONTENT_FULLY_ENQUEUED
        defaultLeassetsMessageTokenShouldBeFound("contentFullyEnqueued.equals=" + DEFAULT_CONTENT_FULLY_ENQUEUED);

        // Get all the leassetsMessageTokenList where contentFullyEnqueued equals to UPDATED_CONTENT_FULLY_ENQUEUED
        defaultLeassetsMessageTokenShouldNotBeFound("contentFullyEnqueued.equals=" + UPDATED_CONTENT_FULLY_ENQUEUED);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByContentFullyEnqueuedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where contentFullyEnqueued not equals to DEFAULT_CONTENT_FULLY_ENQUEUED
        defaultLeassetsMessageTokenShouldNotBeFound("contentFullyEnqueued.notEquals=" + DEFAULT_CONTENT_FULLY_ENQUEUED);

        // Get all the leassetsMessageTokenList where contentFullyEnqueued not equals to UPDATED_CONTENT_FULLY_ENQUEUED
        defaultLeassetsMessageTokenShouldBeFound("contentFullyEnqueued.notEquals=" + UPDATED_CONTENT_FULLY_ENQUEUED);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByContentFullyEnqueuedIsInShouldWork() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where contentFullyEnqueued in DEFAULT_CONTENT_FULLY_ENQUEUED or UPDATED_CONTENT_FULLY_ENQUEUED
        defaultLeassetsMessageTokenShouldBeFound(
            "contentFullyEnqueued.in=" + DEFAULT_CONTENT_FULLY_ENQUEUED + "," + UPDATED_CONTENT_FULLY_ENQUEUED
        );

        // Get all the leassetsMessageTokenList where contentFullyEnqueued equals to UPDATED_CONTENT_FULLY_ENQUEUED
        defaultLeassetsMessageTokenShouldNotBeFound("contentFullyEnqueued.in=" + UPDATED_CONTENT_FULLY_ENQUEUED);
    }

    @Test
    @Transactional
    void getAllLeassetsMessageTokensByContentFullyEnqueuedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        // Get all the leassetsMessageTokenList where contentFullyEnqueued is not null
        defaultLeassetsMessageTokenShouldBeFound("contentFullyEnqueued.specified=true");

        // Get all the leassetsMessageTokenList where contentFullyEnqueued is null
        defaultLeassetsMessageTokenShouldNotBeFound("contentFullyEnqueued.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeassetsMessageTokenShouldBeFound(String filter) throws Exception {
        restLeassetsMessageTokenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leassetsMessageToken.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].timeSent").value(hasItem(DEFAULT_TIME_SENT.intValue())))
            .andExpect(jsonPath("$.[*].tokenValue").value(hasItem(DEFAULT_TOKEN_VALUE)))
            .andExpect(jsonPath("$.[*].received").value(hasItem(DEFAULT_RECEIVED.booleanValue())))
            .andExpect(jsonPath("$.[*].actioned").value(hasItem(DEFAULT_ACTIONED.booleanValue())))
            .andExpect(jsonPath("$.[*].contentFullyEnqueued").value(hasItem(DEFAULT_CONTENT_FULLY_ENQUEUED.booleanValue())));

        // Check, that the count call also returns 1
        restLeassetsMessageTokenMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeassetsMessageTokenShouldNotBeFound(String filter) throws Exception {
        restLeassetsMessageTokenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeassetsMessageTokenMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeassetsMessageToken() throws Exception {
        // Get the leassetsMessageToken
        restLeassetsMessageTokenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLeassetsMessageToken() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        int databaseSizeBeforeUpdate = leassetsMessageTokenRepository.findAll().size();

        // Update the leassetsMessageToken
        LeassetsMessageToken updatedLeassetsMessageToken = leassetsMessageTokenRepository.findById(leassetsMessageToken.getId()).get();
        // Disconnect from session so that the updates on updatedLeassetsMessageToken are not directly saved in db
        em.detach(updatedLeassetsMessageToken);
        updatedLeassetsMessageToken
            .description(UPDATED_DESCRIPTION)
            .timeSent(UPDATED_TIME_SENT)
            .tokenValue(UPDATED_TOKEN_VALUE)
            .received(UPDATED_RECEIVED)
            .actioned(UPDATED_ACTIONED)
            .contentFullyEnqueued(UPDATED_CONTENT_FULLY_ENQUEUED);
        LeassetsMessageTokenDTO leassetsMessageTokenDTO = leassetsMessageTokenMapper.toDto(updatedLeassetsMessageToken);

        restLeassetsMessageTokenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leassetsMessageTokenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leassetsMessageTokenDTO))
            )
            .andExpect(status().isOk());

        // Validate the LeassetsMessageToken in the database
        List<LeassetsMessageToken> leassetsMessageTokenList = leassetsMessageTokenRepository.findAll();
        assertThat(leassetsMessageTokenList).hasSize(databaseSizeBeforeUpdate);
        LeassetsMessageToken testLeassetsMessageToken = leassetsMessageTokenList.get(leassetsMessageTokenList.size() - 1);
        assertThat(testLeassetsMessageToken.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLeassetsMessageToken.getTimeSent()).isEqualTo(UPDATED_TIME_SENT);
        assertThat(testLeassetsMessageToken.getTokenValue()).isEqualTo(UPDATED_TOKEN_VALUE);
        assertThat(testLeassetsMessageToken.getReceived()).isEqualTo(UPDATED_RECEIVED);
        assertThat(testLeassetsMessageToken.getActioned()).isEqualTo(UPDATED_ACTIONED);
        assertThat(testLeassetsMessageToken.getContentFullyEnqueued()).isEqualTo(UPDATED_CONTENT_FULLY_ENQUEUED);

        // Validate the LeassetsMessageToken in Elasticsearch
        verify(mockLeassetsMessageTokenSearchRepository).save(testLeassetsMessageToken);
    }

    @Test
    @Transactional
    void putNonExistingLeassetsMessageToken() throws Exception {
        int databaseSizeBeforeUpdate = leassetsMessageTokenRepository.findAll().size();
        leassetsMessageToken.setId(count.incrementAndGet());

        // Create the LeassetsMessageToken
        LeassetsMessageTokenDTO leassetsMessageTokenDTO = leassetsMessageTokenMapper.toDto(leassetsMessageToken);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeassetsMessageTokenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leassetsMessageTokenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leassetsMessageTokenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeassetsMessageToken in the database
        List<LeassetsMessageToken> leassetsMessageTokenList = leassetsMessageTokenRepository.findAll();
        assertThat(leassetsMessageTokenList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LeassetsMessageToken in Elasticsearch
        verify(mockLeassetsMessageTokenSearchRepository, times(0)).save(leassetsMessageToken);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeassetsMessageToken() throws Exception {
        int databaseSizeBeforeUpdate = leassetsMessageTokenRepository.findAll().size();
        leassetsMessageToken.setId(count.incrementAndGet());

        // Create the LeassetsMessageToken
        LeassetsMessageTokenDTO leassetsMessageTokenDTO = leassetsMessageTokenMapper.toDto(leassetsMessageToken);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeassetsMessageTokenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leassetsMessageTokenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeassetsMessageToken in the database
        List<LeassetsMessageToken> leassetsMessageTokenList = leassetsMessageTokenRepository.findAll();
        assertThat(leassetsMessageTokenList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LeassetsMessageToken in Elasticsearch
        verify(mockLeassetsMessageTokenSearchRepository, times(0)).save(leassetsMessageToken);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeassetsMessageToken() throws Exception {
        int databaseSizeBeforeUpdate = leassetsMessageTokenRepository.findAll().size();
        leassetsMessageToken.setId(count.incrementAndGet());

        // Create the LeassetsMessageToken
        LeassetsMessageTokenDTO leassetsMessageTokenDTO = leassetsMessageTokenMapper.toDto(leassetsMessageToken);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeassetsMessageTokenMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leassetsMessageTokenDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeassetsMessageToken in the database
        List<LeassetsMessageToken> leassetsMessageTokenList = leassetsMessageTokenRepository.findAll();
        assertThat(leassetsMessageTokenList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LeassetsMessageToken in Elasticsearch
        verify(mockLeassetsMessageTokenSearchRepository, times(0)).save(leassetsMessageToken);
    }

    @Test
    @Transactional
    void partialUpdateLeassetsMessageTokenWithPatch() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        int databaseSizeBeforeUpdate = leassetsMessageTokenRepository.findAll().size();

        // Update the leassetsMessageToken using partial update
        LeassetsMessageToken partialUpdatedLeassetsMessageToken = new LeassetsMessageToken();
        partialUpdatedLeassetsMessageToken.setId(leassetsMessageToken.getId());

        partialUpdatedLeassetsMessageToken.description(UPDATED_DESCRIPTION).tokenValue(UPDATED_TOKEN_VALUE).received(UPDATED_RECEIVED);

        restLeassetsMessageTokenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeassetsMessageToken.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeassetsMessageToken))
            )
            .andExpect(status().isOk());

        // Validate the LeassetsMessageToken in the database
        List<LeassetsMessageToken> leassetsMessageTokenList = leassetsMessageTokenRepository.findAll();
        assertThat(leassetsMessageTokenList).hasSize(databaseSizeBeforeUpdate);
        LeassetsMessageToken testLeassetsMessageToken = leassetsMessageTokenList.get(leassetsMessageTokenList.size() - 1);
        assertThat(testLeassetsMessageToken.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLeassetsMessageToken.getTimeSent()).isEqualTo(DEFAULT_TIME_SENT);
        assertThat(testLeassetsMessageToken.getTokenValue()).isEqualTo(UPDATED_TOKEN_VALUE);
        assertThat(testLeassetsMessageToken.getReceived()).isEqualTo(UPDATED_RECEIVED);
        assertThat(testLeassetsMessageToken.getActioned()).isEqualTo(DEFAULT_ACTIONED);
        assertThat(testLeassetsMessageToken.getContentFullyEnqueued()).isEqualTo(DEFAULT_CONTENT_FULLY_ENQUEUED);
    }

    @Test
    @Transactional
    void fullUpdateLeassetsMessageTokenWithPatch() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        int databaseSizeBeforeUpdate = leassetsMessageTokenRepository.findAll().size();

        // Update the leassetsMessageToken using partial update
        LeassetsMessageToken partialUpdatedLeassetsMessageToken = new LeassetsMessageToken();
        partialUpdatedLeassetsMessageToken.setId(leassetsMessageToken.getId());

        partialUpdatedLeassetsMessageToken
            .description(UPDATED_DESCRIPTION)
            .timeSent(UPDATED_TIME_SENT)
            .tokenValue(UPDATED_TOKEN_VALUE)
            .received(UPDATED_RECEIVED)
            .actioned(UPDATED_ACTIONED)
            .contentFullyEnqueued(UPDATED_CONTENT_FULLY_ENQUEUED);

        restLeassetsMessageTokenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeassetsMessageToken.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeassetsMessageToken))
            )
            .andExpect(status().isOk());

        // Validate the LeassetsMessageToken in the database
        List<LeassetsMessageToken> leassetsMessageTokenList = leassetsMessageTokenRepository.findAll();
        assertThat(leassetsMessageTokenList).hasSize(databaseSizeBeforeUpdate);
        LeassetsMessageToken testLeassetsMessageToken = leassetsMessageTokenList.get(leassetsMessageTokenList.size() - 1);
        assertThat(testLeassetsMessageToken.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLeassetsMessageToken.getTimeSent()).isEqualTo(UPDATED_TIME_SENT);
        assertThat(testLeassetsMessageToken.getTokenValue()).isEqualTo(UPDATED_TOKEN_VALUE);
        assertThat(testLeassetsMessageToken.getReceived()).isEqualTo(UPDATED_RECEIVED);
        assertThat(testLeassetsMessageToken.getActioned()).isEqualTo(UPDATED_ACTIONED);
        assertThat(testLeassetsMessageToken.getContentFullyEnqueued()).isEqualTo(UPDATED_CONTENT_FULLY_ENQUEUED);
    }

    @Test
    @Transactional
    void patchNonExistingLeassetsMessageToken() throws Exception {
        int databaseSizeBeforeUpdate = leassetsMessageTokenRepository.findAll().size();
        leassetsMessageToken.setId(count.incrementAndGet());

        // Create the LeassetsMessageToken
        LeassetsMessageTokenDTO leassetsMessageTokenDTO = leassetsMessageTokenMapper.toDto(leassetsMessageToken);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeassetsMessageTokenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leassetsMessageTokenDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leassetsMessageTokenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeassetsMessageToken in the database
        List<LeassetsMessageToken> leassetsMessageTokenList = leassetsMessageTokenRepository.findAll();
        assertThat(leassetsMessageTokenList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LeassetsMessageToken in Elasticsearch
        verify(mockLeassetsMessageTokenSearchRepository, times(0)).save(leassetsMessageToken);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeassetsMessageToken() throws Exception {
        int databaseSizeBeforeUpdate = leassetsMessageTokenRepository.findAll().size();
        leassetsMessageToken.setId(count.incrementAndGet());

        // Create the LeassetsMessageToken
        LeassetsMessageTokenDTO leassetsMessageTokenDTO = leassetsMessageTokenMapper.toDto(leassetsMessageToken);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeassetsMessageTokenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leassetsMessageTokenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeassetsMessageToken in the database
        List<LeassetsMessageToken> leassetsMessageTokenList = leassetsMessageTokenRepository.findAll();
        assertThat(leassetsMessageTokenList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LeassetsMessageToken in Elasticsearch
        verify(mockLeassetsMessageTokenSearchRepository, times(0)).save(leassetsMessageToken);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeassetsMessageToken() throws Exception {
        int databaseSizeBeforeUpdate = leassetsMessageTokenRepository.findAll().size();
        leassetsMessageToken.setId(count.incrementAndGet());

        // Create the LeassetsMessageToken
        LeassetsMessageTokenDTO leassetsMessageTokenDTO = leassetsMessageTokenMapper.toDto(leassetsMessageToken);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeassetsMessageTokenMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leassetsMessageTokenDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeassetsMessageToken in the database
        List<LeassetsMessageToken> leassetsMessageTokenList = leassetsMessageTokenRepository.findAll();
        assertThat(leassetsMessageTokenList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LeassetsMessageToken in Elasticsearch
        verify(mockLeassetsMessageTokenSearchRepository, times(0)).save(leassetsMessageToken);
    }

    @Test
    @Transactional
    void deleteLeassetsMessageToken() throws Exception {
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);

        int databaseSizeBeforeDelete = leassetsMessageTokenRepository.findAll().size();

        // Delete the leassetsMessageToken
        restLeassetsMessageTokenMockMvc
            .perform(delete(ENTITY_API_URL_ID, leassetsMessageToken.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeassetsMessageToken> leassetsMessageTokenList = leassetsMessageTokenRepository.findAll();
        assertThat(leassetsMessageTokenList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LeassetsMessageToken in Elasticsearch
        verify(mockLeassetsMessageTokenSearchRepository, times(1)).deleteById(leassetsMessageToken.getId());
    }

    @Test
    @Transactional
    void searchLeassetsMessageToken() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        leassetsMessageTokenRepository.saveAndFlush(leassetsMessageToken);
        when(mockLeassetsMessageTokenSearchRepository.search(queryStringQuery("id:" + leassetsMessageToken.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(leassetsMessageToken), PageRequest.of(0, 1), 1));

        // Search the leassetsMessageToken
        restLeassetsMessageTokenMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + leassetsMessageToken.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leassetsMessageToken.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].timeSent").value(hasItem(DEFAULT_TIME_SENT.intValue())))
            .andExpect(jsonPath("$.[*].tokenValue").value(hasItem(DEFAULT_TOKEN_VALUE)))
            .andExpect(jsonPath("$.[*].received").value(hasItem(DEFAULT_RECEIVED.booleanValue())))
            .andExpect(jsonPath("$.[*].actioned").value(hasItem(DEFAULT_ACTIONED.booleanValue())))
            .andExpect(jsonPath("$.[*].contentFullyEnqueued").value(hasItem(DEFAULT_CONTENT_FULLY_ENQUEUED.booleanValue())));
    }
}
