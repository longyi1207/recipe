import { Component, OnInit, Optional } from '@angular/core';
import { Recipe } from './recipe';
import { RecipeService } from './recipe.service';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  public recipes: Recipe[];
  public editRecipe: Recipe;
  public deleteRecipe: Recipe;
  public recipe: Recipe;
  public steps: String[];
  public img: File;

  constructor(private recipeService: RecipeService){}

  ngOnInit() {
    this.getRecipes();
  }

  public getRecipes(): void {
    this.recipeService.getRecipes().subscribe(
      (response: Recipe[]) => {
        this.recipes = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onAddRecipe(addForm: NgForm): void {
    let recipe = addForm.value
    if (recipe['image']){
      recipe['image'] = recipe['image'].split("\\").pop()
      this.AddImage()
    }
    this.recipeService.addRecipe(recipe).subscribe(
      (response: Recipe) => {
        this.getRecipes();
        addForm.reset();
        document.getElementById("addRecipeModal").click();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      }
    );
  }

  public imageSelected(event: Event){
    this.img = (<HTMLInputElement>event.target).files[0]
  }

  public AddImage(): void {
    const formData = new FormData();
    formData.append('image', this.img, this.img.name);
    this.recipeService.addImage(formData).subscribe(
      () =>  {},
      (error: HttpErrorResponse) => {
      alert(error.message);
    }
    );
  }

  public onUpdateRecipe(editForm: NgForm): void {
    let recipe = editForm.value 
    if (recipe['image']){
      recipe['image'] = recipe['image'].split("\\").pop()
      this.AddImage()
    }
    this.recipeService.updateRecipe(recipe).subscribe(
      (response: Recipe) => {
        this.getRecipes();
        document.getElementById("updateRecipeModal").click();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onDeleteRecipe(recipeId: number): void {
    this.recipeService.deleteRecipe(recipeId).subscribe(
      (response: void) => {
        this.getRecipes();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public searchRecipes(key: string): void {
    console.log(key);
    const results: Recipe[] = [];
    for (const recipe of this.recipes) {
      console.log("Steps"+recipe.steps.toString().toLowerCase())
      if (recipe.ingredients.toString().toLowerCase().indexOf(key.toString().toLowerCase()) !== -1
      || recipe.name.toString().toLowerCase().indexOf(key.toString().toLowerCase()) !== -1){
        results.push(recipe);
      }
    }
    this.recipes = results;
    if (results.length === 0 || !key) {
      this.getRecipes();
    }
  }

  public onOpenModal(recipe: Recipe, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addRecipeModal');
    }
    if (mode === 'edit') {
      this.editRecipe = recipe;
      button.setAttribute('data-target', '#updateRecipeModal');
      event.stopPropagation();
    }
    if (mode === 'delete') {
      this.deleteRecipe = recipe;
      button.setAttribute('data-target', '#deleteRecipeModal');
      event.stopPropagation();
    }
    if (mode === 'view'){
      this.recipe = recipe;
      this.steps = recipe.steps.toString().split(",");
      button.setAttribute('data-target', '#viewRecipeModal');
    }
    container.appendChild(button);
    button.click();
  }
}