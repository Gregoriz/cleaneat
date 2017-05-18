package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.IngredientRecipe;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the IngredientRecipe entity.
 */
@SuppressWarnings("unused")
public interface IngredientRecipeRepository extends JpaRepository<IngredientRecipe,Long> {

}
