package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.IngredientRecipe;

import com.mycompany.myapp.repository.IngredientRecipeRepository;
import com.mycompany.myapp.repository.search.IngredientRecipeSearchRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing IngredientRecipe.
 */
@RestController
@RequestMapping("/api")
public class IngredientRecipeResource {

    private final Logger log = LoggerFactory.getLogger(IngredientRecipeResource.class);

    private static final String ENTITY_NAME = "ingredientRecipe";
        
    private final IngredientRecipeRepository ingredientRecipeRepository;

    private final IngredientRecipeSearchRepository ingredientRecipeSearchRepository;

    public IngredientRecipeResource(IngredientRecipeRepository ingredientRecipeRepository, IngredientRecipeSearchRepository ingredientRecipeSearchRepository) {
        this.ingredientRecipeRepository = ingredientRecipeRepository;
        this.ingredientRecipeSearchRepository = ingredientRecipeSearchRepository;
    }

    /**
     * POST  /ingredient-recipes : Create a new ingredientRecipe.
     *
     * @param ingredientRecipe the ingredientRecipe to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ingredientRecipe, or with status 400 (Bad Request) if the ingredientRecipe has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ingredient-recipes")
    @Timed
    public ResponseEntity<IngredientRecipe> createIngredientRecipe(@RequestBody IngredientRecipe ingredientRecipe) throws URISyntaxException {
        log.debug("REST request to save IngredientRecipe : {}", ingredientRecipe);
        if (ingredientRecipe.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ingredientRecipe cannot already have an ID")).body(null);
        }
        IngredientRecipe result = ingredientRecipeRepository.save(ingredientRecipe);
        ingredientRecipeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/ingredient-recipes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ingredient-recipes : Updates an existing ingredientRecipe.
     *
     * @param ingredientRecipe the ingredientRecipe to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ingredientRecipe,
     * or with status 400 (Bad Request) if the ingredientRecipe is not valid,
     * or with status 500 (Internal Server Error) if the ingredientRecipe couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ingredient-recipes")
    @Timed
    public ResponseEntity<IngredientRecipe> updateIngredientRecipe(@RequestBody IngredientRecipe ingredientRecipe) throws URISyntaxException {
        log.debug("REST request to update IngredientRecipe : {}", ingredientRecipe);
        if (ingredientRecipe.getId() == null) {
            return createIngredientRecipe(ingredientRecipe);
        }
        IngredientRecipe result = ingredientRecipeRepository.save(ingredientRecipe);
        ingredientRecipeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ingredientRecipe.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ingredient-recipes : get all the ingredientRecipes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ingredientRecipes in body
     */
    @GetMapping("/ingredient-recipes")
    @Timed
    public List<IngredientRecipe> getAllIngredientRecipes() {
        log.debug("REST request to get all IngredientRecipes");
        List<IngredientRecipe> ingredientRecipes = ingredientRecipeRepository.findAll();
        return ingredientRecipes;
    }

    /**
     * GET  /ingredient-recipes/:id : get the "id" ingredientRecipe.
     *
     * @param id the id of the ingredientRecipe to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ingredientRecipe, or with status 404 (Not Found)
     */
    @GetMapping("/ingredient-recipes/{id}")
    @Timed
    public ResponseEntity<IngredientRecipe> getIngredientRecipe(@PathVariable Long id) {
        log.debug("REST request to get IngredientRecipe : {}", id);
        IngredientRecipe ingredientRecipe = ingredientRecipeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ingredientRecipe));
    }

    /**
     * DELETE  /ingredient-recipes/:id : delete the "id" ingredientRecipe.
     *
     * @param id the id of the ingredientRecipe to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ingredient-recipes/{id}")
    @Timed
    public ResponseEntity<Void> deleteIngredientRecipe(@PathVariable Long id) {
        log.debug("REST request to delete IngredientRecipe : {}", id);
        ingredientRecipeRepository.delete(id);
        ingredientRecipeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ingredient-recipes?query=:query : search for the ingredientRecipe corresponding
     * to the query.
     *
     * @param query the query of the ingredientRecipe search 
     * @return the result of the search
     */
    @GetMapping("/_search/ingredient-recipes")
    @Timed
    public List<IngredientRecipe> searchIngredientRecipes(@RequestParam String query) {
        log.debug("REST request to search IngredientRecipes for query {}", query);
        return StreamSupport
            .stream(ingredientRecipeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
