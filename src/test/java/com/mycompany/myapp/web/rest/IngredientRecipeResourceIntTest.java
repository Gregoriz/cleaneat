package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CleaneatApp;

import com.mycompany.myapp.domain.IngredientRecipe;
import com.mycompany.myapp.repository.IngredientRecipeRepository;
import com.mycompany.myapp.repository.search.IngredientRecipeSearchRepository;
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

import com.mycompany.myapp.domain.enumeration.MeasureUnit;
/**
 * Test class for the IngredientRecipeResource REST controller.
 *
 * @see IngredientRecipeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CleaneatApp.class)
public class IngredientRecipeResourceIntTest {

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    private static final MeasureUnit DEFAULT_MEASURE_UNIT = MeasureUnit.G;
    private static final MeasureUnit UPDATED_MEASURE_UNIT = MeasureUnit.L;

    @Autowired
    private IngredientRecipeRepository ingredientRecipeRepository;

    @Autowired
    private IngredientRecipeSearchRepository ingredientRecipeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIngredientRecipeMockMvc;

    private IngredientRecipe ingredientRecipe;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IngredientRecipeResource ingredientRecipeResource = new IngredientRecipeResource(ingredientRecipeRepository, ingredientRecipeSearchRepository);
        this.restIngredientRecipeMockMvc = MockMvcBuilders.standaloneSetup(ingredientRecipeResource)
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
    public static IngredientRecipe createEntity(EntityManager em) {
        IngredientRecipe ingredientRecipe = new IngredientRecipe()
            .quantity(DEFAULT_QUANTITY)
            .measureUnit(DEFAULT_MEASURE_UNIT);
        return ingredientRecipe;
    }

    @Before
    public void initTest() {
        ingredientRecipeSearchRepository.deleteAll();
        ingredientRecipe = createEntity(em);
    }

    @Test
    @Transactional
    public void createIngredientRecipe() throws Exception {
        int databaseSizeBeforeCreate = ingredientRecipeRepository.findAll().size();

        // Create the IngredientRecipe
        restIngredientRecipeMockMvc.perform(post("/api/ingredient-recipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientRecipe)))
            .andExpect(status().isCreated());

        // Validate the IngredientRecipe in the database
        List<IngredientRecipe> ingredientRecipeList = ingredientRecipeRepository.findAll();
        assertThat(ingredientRecipeList).hasSize(databaseSizeBeforeCreate + 1);
        IngredientRecipe testIngredientRecipe = ingredientRecipeList.get(ingredientRecipeList.size() - 1);
        assertThat(testIngredientRecipe.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testIngredientRecipe.getMeasureUnit()).isEqualTo(DEFAULT_MEASURE_UNIT);

        // Validate the IngredientRecipe in Elasticsearch
        IngredientRecipe ingredientRecipeEs = ingredientRecipeSearchRepository.findOne(testIngredientRecipe.getId());
        assertThat(ingredientRecipeEs).isEqualToComparingFieldByField(testIngredientRecipe);
    }

    @Test
    @Transactional
    public void createIngredientRecipeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ingredientRecipeRepository.findAll().size();

        // Create the IngredientRecipe with an existing ID
        ingredientRecipe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredientRecipeMockMvc.perform(post("/api/ingredient-recipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientRecipe)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<IngredientRecipe> ingredientRecipeList = ingredientRecipeRepository.findAll();
        assertThat(ingredientRecipeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIngredientRecipes() throws Exception {
        // Initialize the database
        ingredientRecipeRepository.saveAndFlush(ingredientRecipe);

        // Get all the ingredientRecipeList
        restIngredientRecipeMockMvc.perform(get("/api/ingredient-recipes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredientRecipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].measureUnit").value(hasItem(DEFAULT_MEASURE_UNIT.toString())));
    }

    @Test
    @Transactional
    public void getIngredientRecipe() throws Exception {
        // Initialize the database
        ingredientRecipeRepository.saveAndFlush(ingredientRecipe);

        // Get the ingredientRecipe
        restIngredientRecipeMockMvc.perform(get("/api/ingredient-recipes/{id}", ingredientRecipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ingredientRecipe.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.measureUnit").value(DEFAULT_MEASURE_UNIT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIngredientRecipe() throws Exception {
        // Get the ingredientRecipe
        restIngredientRecipeMockMvc.perform(get("/api/ingredient-recipes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIngredientRecipe() throws Exception {
        // Initialize the database
        ingredientRecipeRepository.saveAndFlush(ingredientRecipe);
        ingredientRecipeSearchRepository.save(ingredientRecipe);
        int databaseSizeBeforeUpdate = ingredientRecipeRepository.findAll().size();

        // Update the ingredientRecipe
        IngredientRecipe updatedIngredientRecipe = ingredientRecipeRepository.findOne(ingredientRecipe.getId());
        updatedIngredientRecipe
            .quantity(UPDATED_QUANTITY)
            .measureUnit(UPDATED_MEASURE_UNIT);

        restIngredientRecipeMockMvc.perform(put("/api/ingredient-recipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIngredientRecipe)))
            .andExpect(status().isOk());

        // Validate the IngredientRecipe in the database
        List<IngredientRecipe> ingredientRecipeList = ingredientRecipeRepository.findAll();
        assertThat(ingredientRecipeList).hasSize(databaseSizeBeforeUpdate);
        IngredientRecipe testIngredientRecipe = ingredientRecipeList.get(ingredientRecipeList.size() - 1);
        assertThat(testIngredientRecipe.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testIngredientRecipe.getMeasureUnit()).isEqualTo(UPDATED_MEASURE_UNIT);

        // Validate the IngredientRecipe in Elasticsearch
        IngredientRecipe ingredientRecipeEs = ingredientRecipeSearchRepository.findOne(testIngredientRecipe.getId());
        assertThat(ingredientRecipeEs).isEqualToComparingFieldByField(testIngredientRecipe);
    }

    @Test
    @Transactional
    public void updateNonExistingIngredientRecipe() throws Exception {
        int databaseSizeBeforeUpdate = ingredientRecipeRepository.findAll().size();

        // Create the IngredientRecipe

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIngredientRecipeMockMvc.perform(put("/api/ingredient-recipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientRecipe)))
            .andExpect(status().isCreated());

        // Validate the IngredientRecipe in the database
        List<IngredientRecipe> ingredientRecipeList = ingredientRecipeRepository.findAll();
        assertThat(ingredientRecipeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIngredientRecipe() throws Exception {
        // Initialize the database
        ingredientRecipeRepository.saveAndFlush(ingredientRecipe);
        ingredientRecipeSearchRepository.save(ingredientRecipe);
        int databaseSizeBeforeDelete = ingredientRecipeRepository.findAll().size();

        // Get the ingredientRecipe
        restIngredientRecipeMockMvc.perform(delete("/api/ingredient-recipes/{id}", ingredientRecipe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean ingredientRecipeExistsInEs = ingredientRecipeSearchRepository.exists(ingredientRecipe.getId());
        assertThat(ingredientRecipeExistsInEs).isFalse();

        // Validate the database is empty
        List<IngredientRecipe> ingredientRecipeList = ingredientRecipeRepository.findAll();
        assertThat(ingredientRecipeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIngredientRecipe() throws Exception {
        // Initialize the database
        ingredientRecipeRepository.saveAndFlush(ingredientRecipe);
        ingredientRecipeSearchRepository.save(ingredientRecipe);

        // Search the ingredientRecipe
        restIngredientRecipeMockMvc.perform(get("/api/_search/ingredient-recipes?query=id:" + ingredientRecipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredientRecipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].measureUnit").value(hasItem(DEFAULT_MEASURE_UNIT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientRecipe.class);
        IngredientRecipe ingredientRecipe1 = new IngredientRecipe();
        ingredientRecipe1.setId(1L);
        IngredientRecipe ingredientRecipe2 = new IngredientRecipe();
        ingredientRecipe2.setId(ingredientRecipe1.getId());
        assertThat(ingredientRecipe1).isEqualTo(ingredientRecipe2);
        ingredientRecipe2.setId(2L);
        assertThat(ingredientRecipe1).isNotEqualTo(ingredientRecipe2);
        ingredientRecipe1.setId(null);
        assertThat(ingredientRecipe1).isNotEqualTo(ingredientRecipe2);
    }
}
