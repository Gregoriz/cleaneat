package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.IngredientRecipe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the IngredientRecipe entity.
 */
public interface IngredientRecipeSearchRepository extends ElasticsearchRepository<IngredientRecipe, Long> {
}
