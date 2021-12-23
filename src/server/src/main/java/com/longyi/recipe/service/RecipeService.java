package com.longyi.recipe.service;

import com.longyi.recipe.model.Recipe;
import com.longyi.recipe.repo.RecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

@Service
@Transactional
public class RecipeService {
    private final RecipeRepo recipeRepo;

    @Autowired
    public RecipeService(RecipeRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    public Recipe addRecipe(Recipe recipe){
        return recipeRepo.save(recipe);
    }

    public List<Recipe> getAllRecipe(){
        return recipeRepo.findAll();
    }

    public Recipe getRecipeById(Long id) throws Exception {
        return recipeRepo.findById(id)
                .orElseThrow(() -> new Exception("Recipe not exist"));
    }

    public Recipe updateRecipe(Recipe recipe){
        return recipeRepo.save(recipe);
    }

    public void deleteRecipe(Long id){
        recipeRepo.deleteRecipeById(id);
    }

}
