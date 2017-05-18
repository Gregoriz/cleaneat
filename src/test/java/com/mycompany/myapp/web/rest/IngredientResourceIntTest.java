package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CleaneatApp;

import com.mycompany.myapp.domain.Ingredient;
import com.mycompany.myapp.repository.IngredientRepository;
import com.mycompany.myapp.repository.search.IngredientSearchRepository;
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
 * Test class for the IngredientResource REST controller.
 *
 * @see IngredientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CleaneatApp.class)
public class IngredientResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_AVERAGE_WEIGTH = 1D;
    private static final Double UPDATED_AVERAGE_WEIGTH = 2D;

    private static final MeasureUnit DEFAULT_AVERAGE_WEIGHT_UNIT = MeasureUnit.G;
    private static final MeasureUnit UPDATED_AVERAGE_WEIGHT_UNIT = MeasureUnit.L;

    private static final Double DEFAULT_PROTEIN = 1D;
    private static final Double UPDATED_PROTEIN = 2D;

    private static final Double DEFAULT_LIPID = 1D;
    private static final Double UPDATED_LIPID = 2D;

    private static final Double DEFAULT_SATURATED_FATTY_ACID = 1D;
    private static final Double UPDATED_SATURATED_FATTY_ACID = 2D;

    private static final Double DEFAULT_POLYUNSATURATED_FATTY_ACIDS = 1D;
    private static final Double UPDATED_POLYUNSATURATED_FATTY_ACIDS = 2D;

    private static final Double DEFAULT_SATURATED_FATS = 1D;
    private static final Double UPDATED_SATURATED_FATS = 2D;

    private static final Double DEFAULT_GLUCID = 1D;
    private static final Double UPDATED_GLUCID = 2D;

    private static final Double DEFAULT_SUGAR = 1D;
    private static final Double UPDATED_SUGAR = 2D;

    private static final Double DEFAULT_FIBER = 1D;
    private static final Double UPDATED_FIBER = 2D;

    private static final Integer DEFAULT_POTASSIUM = 1;
    private static final Integer UPDATED_POTASSIUM = 2;

    private static final Integer DEFAULT_SODIUM = 1;
    private static final Integer UPDATED_SODIUM = 2;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientSearchRepository ingredientSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIngredientMockMvc;

    private Ingredient ingredient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IngredientResource ingredientResource = new IngredientResource(ingredientRepository, ingredientSearchRepository);
        this.restIngredientMockMvc = MockMvcBuilders.standaloneSetup(ingredientResource)
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
    public static Ingredient createEntity(EntityManager em) {
        Ingredient ingredient = new Ingredient()
            .name(DEFAULT_NAME)
            .averageWeigth(DEFAULT_AVERAGE_WEIGTH)
            .averageWeightUnit(DEFAULT_AVERAGE_WEIGHT_UNIT)
            .protein(DEFAULT_PROTEIN)
            .lipid(DEFAULT_LIPID)
            .saturatedFattyAcid(DEFAULT_SATURATED_FATTY_ACID)
            .polyunsaturatedFattyAcids(DEFAULT_POLYUNSATURATED_FATTY_ACIDS)
            .saturatedFats(DEFAULT_SATURATED_FATS)
            .glucid(DEFAULT_GLUCID)
            .sugar(DEFAULT_SUGAR)
            .fiber(DEFAULT_FIBER)
            .potassium(DEFAULT_POTASSIUM)
            .sodium(DEFAULT_SODIUM);
        return ingredient;
    }

    @Before
    public void initTest() {
        ingredientSearchRepository.deleteAll();
        ingredient = createEntity(em);
    }

    @Test
    @Transactional
    public void createIngredient() throws Exception {
        int databaseSizeBeforeCreate = ingredientRepository.findAll().size();

        // Create the Ingredient
        restIngredientMockMvc.perform(post("/api/ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredient)))
            .andExpect(status().isCreated());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeCreate + 1);
        Ingredient testIngredient = ingredientList.get(ingredientList.size() - 1);
        assertThat(testIngredient.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIngredient.getAverageWeigth()).isEqualTo(DEFAULT_AVERAGE_WEIGTH);
        assertThat(testIngredient.getAverageWeightUnit()).isEqualTo(DEFAULT_AVERAGE_WEIGHT_UNIT);
        assertThat(testIngredient.getProtein()).isEqualTo(DEFAULT_PROTEIN);
        assertThat(testIngredient.getLipid()).isEqualTo(DEFAULT_LIPID);
        assertThat(testIngredient.getSaturatedFattyAcid()).isEqualTo(DEFAULT_SATURATED_FATTY_ACID);
        assertThat(testIngredient.getPolyunsaturatedFattyAcids()).isEqualTo(DEFAULT_POLYUNSATURATED_FATTY_ACIDS);
        assertThat(testIngredient.getSaturatedFats()).isEqualTo(DEFAULT_SATURATED_FATS);
        assertThat(testIngredient.getGlucid()).isEqualTo(DEFAULT_GLUCID);
        assertThat(testIngredient.getSugar()).isEqualTo(DEFAULT_SUGAR);
        assertThat(testIngredient.getFiber()).isEqualTo(DEFAULT_FIBER);
        assertThat(testIngredient.getPotassium()).isEqualTo(DEFAULT_POTASSIUM);
        assertThat(testIngredient.getSodium()).isEqualTo(DEFAULT_SODIUM);

        // Validate the Ingredient in Elasticsearch
        Ingredient ingredientEs = ingredientSearchRepository.findOne(testIngredient.getId());
        assertThat(ingredientEs).isEqualToComparingFieldByField(testIngredient);
    }

    @Test
    @Transactional
    public void createIngredientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ingredientRepository.findAll().size();

        // Create the Ingredient with an existing ID
        ingredient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredientMockMvc.perform(post("/api/ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredient)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientRepository.findAll().size();
        // set the field null
        ingredient.setName(null);

        // Create the Ingredient, which fails.

        restIngredientMockMvc.perform(post("/api/ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredient)))
            .andExpect(status().isBadRequest());

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProteinIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientRepository.findAll().size();
        // set the field null
        ingredient.setProtein(null);

        // Create the Ingredient, which fails.

        restIngredientMockMvc.perform(post("/api/ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredient)))
            .andExpect(status().isBadRequest());

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLipidIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientRepository.findAll().size();
        // set the field null
        ingredient.setLipid(null);

        // Create the Ingredient, which fails.

        restIngredientMockMvc.perform(post("/api/ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredient)))
            .andExpect(status().isBadRequest());

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSaturatedFattyAcidIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientRepository.findAll().size();
        // set the field null
        ingredient.setSaturatedFattyAcid(null);

        // Create the Ingredient, which fails.

        restIngredientMockMvc.perform(post("/api/ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredient)))
            .andExpect(status().isBadRequest());

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPolyunsaturatedFattyAcidsIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientRepository.findAll().size();
        // set the field null
        ingredient.setPolyunsaturatedFattyAcids(null);

        // Create the Ingredient, which fails.

        restIngredientMockMvc.perform(post("/api/ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredient)))
            .andExpect(status().isBadRequest());

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSaturatedFatsIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientRepository.findAll().size();
        // set the field null
        ingredient.setSaturatedFats(null);

        // Create the Ingredient, which fails.

        restIngredientMockMvc.perform(post("/api/ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredient)))
            .andExpect(status().isBadRequest());

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGlucidIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientRepository.findAll().size();
        // set the field null
        ingredient.setGlucid(null);

        // Create the Ingredient, which fails.

        restIngredientMockMvc.perform(post("/api/ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredient)))
            .andExpect(status().isBadRequest());

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSugarIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientRepository.findAll().size();
        // set the field null
        ingredient.setSugar(null);

        // Create the Ingredient, which fails.

        restIngredientMockMvc.perform(post("/api/ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredient)))
            .andExpect(status().isBadRequest());

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFiberIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientRepository.findAll().size();
        // set the field null
        ingredient.setFiber(null);

        // Create the Ingredient, which fails.

        restIngredientMockMvc.perform(post("/api/ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredient)))
            .andExpect(status().isBadRequest());

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPotassiumIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientRepository.findAll().size();
        // set the field null
        ingredient.setPotassium(null);

        // Create the Ingredient, which fails.

        restIngredientMockMvc.perform(post("/api/ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredient)))
            .andExpect(status().isBadRequest());

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSodiumIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientRepository.findAll().size();
        // set the field null
        ingredient.setSodium(null);

        // Create the Ingredient, which fails.

        restIngredientMockMvc.perform(post("/api/ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredient)))
            .andExpect(status().isBadRequest());

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIngredients() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get all the ingredientList
        restIngredientMockMvc.perform(get("/api/ingredients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredient.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].averageWeigth").value(hasItem(DEFAULT_AVERAGE_WEIGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].averageWeightUnit").value(hasItem(DEFAULT_AVERAGE_WEIGHT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].protein").value(hasItem(DEFAULT_PROTEIN.doubleValue())))
            .andExpect(jsonPath("$.[*].lipid").value(hasItem(DEFAULT_LIPID.doubleValue())))
            .andExpect(jsonPath("$.[*].saturatedFattyAcid").value(hasItem(DEFAULT_SATURATED_FATTY_ACID.doubleValue())))
            .andExpect(jsonPath("$.[*].polyunsaturatedFattyAcids").value(hasItem(DEFAULT_POLYUNSATURATED_FATTY_ACIDS.doubleValue())))
            .andExpect(jsonPath("$.[*].saturatedFats").value(hasItem(DEFAULT_SATURATED_FATS.doubleValue())))
            .andExpect(jsonPath("$.[*].glucid").value(hasItem(DEFAULT_GLUCID.doubleValue())))
            .andExpect(jsonPath("$.[*].sugar").value(hasItem(DEFAULT_SUGAR.doubleValue())))
            .andExpect(jsonPath("$.[*].fiber").value(hasItem(DEFAULT_FIBER.doubleValue())))
            .andExpect(jsonPath("$.[*].potassium").value(hasItem(DEFAULT_POTASSIUM)))
            .andExpect(jsonPath("$.[*].sodium").value(hasItem(DEFAULT_SODIUM)));
    }

    @Test
    @Transactional
    public void getIngredient() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get the ingredient
        restIngredientMockMvc.perform(get("/api/ingredients/{id}", ingredient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ingredient.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.averageWeigth").value(DEFAULT_AVERAGE_WEIGTH.doubleValue()))
            .andExpect(jsonPath("$.averageWeightUnit").value(DEFAULT_AVERAGE_WEIGHT_UNIT.toString()))
            .andExpect(jsonPath("$.protein").value(DEFAULT_PROTEIN.doubleValue()))
            .andExpect(jsonPath("$.lipid").value(DEFAULT_LIPID.doubleValue()))
            .andExpect(jsonPath("$.saturatedFattyAcid").value(DEFAULT_SATURATED_FATTY_ACID.doubleValue()))
            .andExpect(jsonPath("$.polyunsaturatedFattyAcids").value(DEFAULT_POLYUNSATURATED_FATTY_ACIDS.doubleValue()))
            .andExpect(jsonPath("$.saturatedFats").value(DEFAULT_SATURATED_FATS.doubleValue()))
            .andExpect(jsonPath("$.glucid").value(DEFAULT_GLUCID.doubleValue()))
            .andExpect(jsonPath("$.sugar").value(DEFAULT_SUGAR.doubleValue()))
            .andExpect(jsonPath("$.fiber").value(DEFAULT_FIBER.doubleValue()))
            .andExpect(jsonPath("$.potassium").value(DEFAULT_POTASSIUM))
            .andExpect(jsonPath("$.sodium").value(DEFAULT_SODIUM));
    }

    @Test
    @Transactional
    public void getNonExistingIngredient() throws Exception {
        // Get the ingredient
        restIngredientMockMvc.perform(get("/api/ingredients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIngredient() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);
        ingredientSearchRepository.save(ingredient);
        int databaseSizeBeforeUpdate = ingredientRepository.findAll().size();

        // Update the ingredient
        Ingredient updatedIngredient = ingredientRepository.findOne(ingredient.getId());
        updatedIngredient
            .name(UPDATED_NAME)
            .averageWeigth(UPDATED_AVERAGE_WEIGTH)
            .averageWeightUnit(UPDATED_AVERAGE_WEIGHT_UNIT)
            .protein(UPDATED_PROTEIN)
            .lipid(UPDATED_LIPID)
            .saturatedFattyAcid(UPDATED_SATURATED_FATTY_ACID)
            .polyunsaturatedFattyAcids(UPDATED_POLYUNSATURATED_FATTY_ACIDS)
            .saturatedFats(UPDATED_SATURATED_FATS)
            .glucid(UPDATED_GLUCID)
            .sugar(UPDATED_SUGAR)
            .fiber(UPDATED_FIBER)
            .potassium(UPDATED_POTASSIUM)
            .sodium(UPDATED_SODIUM);

        restIngredientMockMvc.perform(put("/api/ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIngredient)))
            .andExpect(status().isOk());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeUpdate);
        Ingredient testIngredient = ingredientList.get(ingredientList.size() - 1);
        assertThat(testIngredient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIngredient.getAverageWeigth()).isEqualTo(UPDATED_AVERAGE_WEIGTH);
        assertThat(testIngredient.getAverageWeightUnit()).isEqualTo(UPDATED_AVERAGE_WEIGHT_UNIT);
        assertThat(testIngredient.getProtein()).isEqualTo(UPDATED_PROTEIN);
        assertThat(testIngredient.getLipid()).isEqualTo(UPDATED_LIPID);
        assertThat(testIngredient.getSaturatedFattyAcid()).isEqualTo(UPDATED_SATURATED_FATTY_ACID);
        assertThat(testIngredient.getPolyunsaturatedFattyAcids()).isEqualTo(UPDATED_POLYUNSATURATED_FATTY_ACIDS);
        assertThat(testIngredient.getSaturatedFats()).isEqualTo(UPDATED_SATURATED_FATS);
        assertThat(testIngredient.getGlucid()).isEqualTo(UPDATED_GLUCID);
        assertThat(testIngredient.getSugar()).isEqualTo(UPDATED_SUGAR);
        assertThat(testIngredient.getFiber()).isEqualTo(UPDATED_FIBER);
        assertThat(testIngredient.getPotassium()).isEqualTo(UPDATED_POTASSIUM);
        assertThat(testIngredient.getSodium()).isEqualTo(UPDATED_SODIUM);

        // Validate the Ingredient in Elasticsearch
        Ingredient ingredientEs = ingredientSearchRepository.findOne(testIngredient.getId());
        assertThat(ingredientEs).isEqualToComparingFieldByField(testIngredient);
    }

    @Test
    @Transactional
    public void updateNonExistingIngredient() throws Exception {
        int databaseSizeBeforeUpdate = ingredientRepository.findAll().size();

        // Create the Ingredient

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIngredientMockMvc.perform(put("/api/ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredient)))
            .andExpect(status().isCreated());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIngredient() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);
        ingredientSearchRepository.save(ingredient);
        int databaseSizeBeforeDelete = ingredientRepository.findAll().size();

        // Get the ingredient
        restIngredientMockMvc.perform(delete("/api/ingredients/{id}", ingredient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean ingredientExistsInEs = ingredientSearchRepository.exists(ingredient.getId());
        assertThat(ingredientExistsInEs).isFalse();

        // Validate the database is empty
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIngredient() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);
        ingredientSearchRepository.save(ingredient);

        // Search the ingredient
        restIngredientMockMvc.perform(get("/api/_search/ingredients?query=id:" + ingredient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredient.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].averageWeigth").value(hasItem(DEFAULT_AVERAGE_WEIGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].averageWeightUnit").value(hasItem(DEFAULT_AVERAGE_WEIGHT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].protein").value(hasItem(DEFAULT_PROTEIN.doubleValue())))
            .andExpect(jsonPath("$.[*].lipid").value(hasItem(DEFAULT_LIPID.doubleValue())))
            .andExpect(jsonPath("$.[*].saturatedFattyAcid").value(hasItem(DEFAULT_SATURATED_FATTY_ACID.doubleValue())))
            .andExpect(jsonPath("$.[*].polyunsaturatedFattyAcids").value(hasItem(DEFAULT_POLYUNSATURATED_FATTY_ACIDS.doubleValue())))
            .andExpect(jsonPath("$.[*].saturatedFats").value(hasItem(DEFAULT_SATURATED_FATS.doubleValue())))
            .andExpect(jsonPath("$.[*].glucid").value(hasItem(DEFAULT_GLUCID.doubleValue())))
            .andExpect(jsonPath("$.[*].sugar").value(hasItem(DEFAULT_SUGAR.doubleValue())))
            .andExpect(jsonPath("$.[*].fiber").value(hasItem(DEFAULT_FIBER.doubleValue())))
            .andExpect(jsonPath("$.[*].potassium").value(hasItem(DEFAULT_POTASSIUM)))
            .andExpect(jsonPath("$.[*].sodium").value(hasItem(DEFAULT_SODIUM)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ingredient.class);
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(ingredient1.getId());
        assertThat(ingredient1).isEqualTo(ingredient2);
        ingredient2.setId(2L);
        assertThat(ingredient1).isNotEqualTo(ingredient2);
        ingredient1.setId(null);
        assertThat(ingredient1).isNotEqualTo(ingredient2);
    }
}
