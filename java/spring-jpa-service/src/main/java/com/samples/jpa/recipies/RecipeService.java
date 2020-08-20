package com.samples.jpa.recipies;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public void createRecipe(final Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public List<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

}