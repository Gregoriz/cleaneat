package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Recipe.
 */
@Entity
@Table(name = "recipe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "recipe")
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "recipe_tools",
               joinColumns = @JoinColumn(name="recipes_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tools_id", referencedColumnName="id"))
    private Set<Tool> tools = new HashSet<>();

    @OneToMany(mappedBy = "recipe")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IngredientRecipe> ingredients = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Recipe description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Tool> getTools() {
        return tools;
    }

    public Recipe tools(Set<Tool> tools) {
        this.tools = tools;
        return this;
    }

    public Recipe addTools(Tool tool) {
        this.tools.add(tool);
        return this;
    }

    public Recipe removeTools(Tool tool) {
        this.tools.remove(tool);
        return this;
    }

    public void setTools(Set<Tool> tools) {
        this.tools = tools;
    }

    public Set<IngredientRecipe> getIngredients() {
        return ingredients;
    }

    public Recipe ingredients(Set<IngredientRecipe> ingredientRecipes) {
        this.ingredients = ingredientRecipes;
        return this;
    }

    public Recipe addIngredients(IngredientRecipe ingredientRecipe) {
        this.ingredients.add(ingredientRecipe);
        ingredientRecipe.setRecipe(this);
        return this;
    }

    public Recipe removeIngredients(IngredientRecipe ingredientRecipe) {
        this.ingredients.remove(ingredientRecipe);
        ingredientRecipe.setRecipe(null);
        return this;
    }

    public void setIngredients(Set<IngredientRecipe> ingredientRecipes) {
        this.ingredients = ingredientRecipes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Recipe recipe = (Recipe) o;
        if (recipe.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recipe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Recipe{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
