package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CleaneatApp;

import com.mycompany.myapp.domain.UserDummy;
import com.mycompany.myapp.repository.UserDummyRepository;
import com.mycompany.myapp.repository.search.UserDummySearchRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserDummyResource REST controller.
 *
 * @see UserDummyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CleaneatApp.class)
public class UserDummyResourceIntTest {

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final Integer DEFAULT_DAILY_ENERGY_REQUIRED = 1;
    private static final Integer UPDATED_DAILY_ENERGY_REQUIRED = 2;

    @Autowired
    private UserDummyRepository userDummyRepository;

    @Autowired
    private UserDummySearchRepository userDummySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserDummyMockMvc;

    private UserDummy userDummy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserDummyResource userDummyResource = new UserDummyResource(userDummyRepository, userDummySearchRepository);
        this.restUserDummyMockMvc = MockMvcBuilders.standaloneSetup(userDummyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserDummy createEntity(EntityManager em) {
        UserDummy userDummy = new UserDummy()
            .weight(DEFAULT_WEIGHT)
            .height(DEFAULT_HEIGHT)
            .dailyEnergyRequired(DEFAULT_DAILY_ENERGY_REQUIRED);
        return userDummy;
    }

    @Before
    public void initTest() {
        userDummySearchRepository.deleteAll();
        userDummy = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserDummy() throws Exception {
        int databaseSizeBeforeCreate = userDummyRepository.findAll().size();

        // Create the UserDummy
        restUserDummyMockMvc.perform(post("/api/user-dummies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDummy)))
            .andExpect(status().isCreated());

        // Validate the UserDummy in the database
        List<UserDummy> userDummyList = userDummyRepository.findAll();
        assertThat(userDummyList).hasSize(databaseSizeBeforeCreate + 1);
        UserDummy testUserDummy = userDummyList.get(userDummyList.size() - 1);
        assertThat(testUserDummy.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testUserDummy.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testUserDummy.getDailyEnergyRequired()).isEqualTo(DEFAULT_DAILY_ENERGY_REQUIRED);

        // Validate the UserDummy in Elasticsearch
        UserDummy userDummyEs = userDummySearchRepository.findOne(testUserDummy.getId());
        assertThat(userDummyEs).isEqualToComparingFieldByField(testUserDummy);
    }

    @Test
    @Transactional
    public void createUserDummyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userDummyRepository.findAll().size();

        // Create the UserDummy with an existing ID
        userDummy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserDummyMockMvc.perform(post("/api/user-dummies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDummy)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserDummy> userDummyList = userDummyRepository.findAll();
        assertThat(userDummyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDummyRepository.findAll().size();
        // set the field null
        userDummy.setWeight(null);

        // Create the UserDummy, which fails.

        restUserDummyMockMvc.perform(post("/api/user-dummies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDummy)))
            .andExpect(status().isBadRequest());

        List<UserDummy> userDummyList = userDummyRepository.findAll();
        assertThat(userDummyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDummyRepository.findAll().size();
        // set the field null
        userDummy.setHeight(null);

        // Create the UserDummy, which fails.

        restUserDummyMockMvc.perform(post("/api/user-dummies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDummy)))
            .andExpect(status().isBadRequest());

        List<UserDummy> userDummyList = userDummyRepository.findAll();
        assertThat(userDummyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDailyEnergyRequiredIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDummyRepository.findAll().size();
        // set the field null
        userDummy.setDailyEnergyRequired(null);

        // Create the UserDummy, which fails.

        restUserDummyMockMvc.perform(post("/api/user-dummies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDummy)))
            .andExpect(status().isBadRequest());

        List<UserDummy> userDummyList = userDummyRepository.findAll();
        assertThat(userDummyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserDummies() throws Exception {
        // Initialize the database
        userDummyRepository.saveAndFlush(userDummy);

        // Get all the userDummyList
        restUserDummyMockMvc.perform(get("/api/user-dummies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userDummy.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].dailyEnergyRequired").value(hasItem(DEFAULT_DAILY_ENERGY_REQUIRED)));
    }

    @Test
    @Transactional
    public void getUserDummy() throws Exception {
        // Initialize the database
        userDummyRepository.saveAndFlush(userDummy);

        // Get the userDummy
        restUserDummyMockMvc.perform(get("/api/user-dummies/{id}", userDummy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userDummy.getId().intValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.dailyEnergyRequired").value(DEFAULT_DAILY_ENERGY_REQUIRED));
    }

    @Test
    @Transactional
    public void getNonExistingUserDummy() throws Exception {
        // Get the userDummy
        restUserDummyMockMvc.perform(get("/api/user-dummies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserDummy() throws Exception {
        // Initialize the database
        userDummyRepository.saveAndFlush(userDummy);
        userDummySearchRepository.save(userDummy);
        int databaseSizeBeforeUpdate = userDummyRepository.findAll().size();

        // Update the userDummy
        UserDummy updatedUserDummy = userDummyRepository.findOne(userDummy.getId());
        updatedUserDummy
            .weight(UPDATED_WEIGHT)
            .height(UPDATED_HEIGHT)
            .dailyEnergyRequired(UPDATED_DAILY_ENERGY_REQUIRED);

        restUserDummyMockMvc.perform(put("/api/user-dummies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserDummy)))
            .andExpect(status().isOk());

        // Validate the UserDummy in the database
        List<UserDummy> userDummyList = userDummyRepository.findAll();
        assertThat(userDummyList).hasSize(databaseSizeBeforeUpdate);
        UserDummy testUserDummy = userDummyList.get(userDummyList.size() - 1);
        assertThat(testUserDummy.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testUserDummy.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testUserDummy.getDailyEnergyRequired()).isEqualTo(UPDATED_DAILY_ENERGY_REQUIRED);

        // Validate the UserDummy in Elasticsearch
        UserDummy userDummyEs = userDummySearchRepository.findOne(testUserDummy.getId());
        assertThat(userDummyEs).isEqualToComparingFieldByField(testUserDummy);
    }

    @Test
    @Transactional
    public void updateNonExistingUserDummy() throws Exception {
        int databaseSizeBeforeUpdate = userDummyRepository.findAll().size();

        // Create the UserDummy

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserDummyMockMvc.perform(put("/api/user-dummies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDummy)))
            .andExpect(status().isCreated());

        // Validate the UserDummy in the database
        List<UserDummy> userDummyList = userDummyRepository.findAll();
        assertThat(userDummyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserDummy() throws Exception {
        // Initialize the database
        userDummyRepository.saveAndFlush(userDummy);
        userDummySearchRepository.save(userDummy);
        int databaseSizeBeforeDelete = userDummyRepository.findAll().size();

        // Get the userDummy
        restUserDummyMockMvc.perform(delete("/api/user-dummies/{id}", userDummy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean userDummyExistsInEs = userDummySearchRepository.exists(userDummy.getId());
        assertThat(userDummyExistsInEs).isFalse();

        // Validate the database is empty
        List<UserDummy> userDummyList = userDummyRepository.findAll();
        assertThat(userDummyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUserDummy() throws Exception {
        // Initialize the database
        userDummyRepository.saveAndFlush(userDummy);
        userDummySearchRepository.save(userDummy);

        // Search the userDummy
        restUserDummyMockMvc.perform(get("/api/_search/user-dummies?query=id:" + userDummy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userDummy.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].dailyEnergyRequired").value(hasItem(DEFAULT_DAILY_ENERGY_REQUIRED)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDummy.class);
        UserDummy userDummy1 = new UserDummy();
        userDummy1.setId(1L);
        UserDummy userDummy2 = new UserDummy();
        userDummy2.setId(userDummy1.getId());
        assertThat(userDummy1).isEqualTo(userDummy2);
        userDummy2.setId(2L);
        assertThat(userDummy1).isNotEqualTo(userDummy2);
        userDummy1.setId(null);
        assertThat(userDummy1).isNotEqualTo(userDummy2);
    }
}
