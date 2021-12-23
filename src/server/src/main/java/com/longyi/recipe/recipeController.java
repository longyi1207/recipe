package com.longyi.recipe;

import com.longyi.recipe.model.Recipe;
import com.longyi.recipe.service.RecipeService;
import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recipe")
public class recipeController {
    private final RecipeService recipeService;

    public recipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/find")
    public ResponseEntity<List<Recipe>> getAllRecipe(){
        List<Recipe> recipes = recipeService.getAllRecipe();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") Long id) throws Exception {
        Recipe recipe = recipeService.getRecipeById(id);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe){
        Recipe newRecipe = recipeService.addRecipe(recipe);
        return new ResponseEntity<>(newRecipe, HttpStatus.CREATED);
    }

    @PostMapping("/addImg")
    public ResponseEntity<Recipe> addRecipe(@RequestParam("image") MultipartFile img) throws IOException {
        String imgName = StringUtils.cleanPath(img.getOriginalFilename());
        System.out.println(imgName);
        String uploadDir = "images/";
        FileUploadUtil.saveFile(uploadDir, imgName, img);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Recipe> updateRecipe(@RequestBody Recipe recipe) throws Exception {
        System.out.println(recipe.getIngredients());
        Recipe oldRecipe = recipeService.getRecipeById(recipe.getId());
        oldRecipe.setAuthor(recipe.getAuthor());
        oldRecipe.setTime(recipe.getTime());
        oldRecipe.setIngredients(recipe.getIngredients());
        oldRecipe.setSteps(recipe.getSteps());
        oldRecipe.setImage(recipe.getImage());
        Recipe updateRecipe = recipeService.updateRecipe(oldRecipe);
        return new ResponseEntity<>(updateRecipe, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable("id") Long id) {
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
