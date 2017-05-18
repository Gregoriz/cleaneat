package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Ingredient;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Ingredient entity.
 */
public interface IngredientSearchRepository extends ElasticsearchRepository<Ingredient, Long> {
}
