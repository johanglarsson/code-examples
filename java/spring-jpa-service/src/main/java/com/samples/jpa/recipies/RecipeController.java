package com.samples.jpa.recipies;

import java.util.List;
import java.util.stream.Collectors;

import com.samples.jpa.recipies.dtos.RecipeDto;

import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    private final ConversionService conversionService;

    @PostMapping("/api/v1/recipies/new")
    public void newRecipe(@RequestBody final RecipeDto recipeDto) {
        recipeService.createRecipe(conversionService.convert(recipeDto, Recipe.class));
    }

    @GetMapping("/api/v1/recipies")
    public List<RecipeDto> getRecipies() {
        return recipeService.getRecipes().stream().map(recipe -> conversionService.convert(recipe, RecipeDto.class))
                .collect(Collectors.toList());
    }

}