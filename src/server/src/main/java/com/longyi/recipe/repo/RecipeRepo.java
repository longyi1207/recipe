package com.longyi.recipe.repo;

import com.longyi.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeRepo extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findById(Long id);
    void deleteRecipeById(Long id);
}
