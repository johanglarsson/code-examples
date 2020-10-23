package com.samples.jpa.recipies;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class DatabaseLoader implements CommandLineRunner {

    private final RecipeRepository recipeRepository;

    @Override
    public void run(String... args) throws Exception {

        Recipe souvlakiRecipe = new Recipe();
        souvlakiRecipe.setDescription("Chicken souvlaki");

        Ingredient chickenIngredient = new Ingredient();
        chickenIngredient.setAmount("1");
        chickenIngredient.setName("Chicken");
        chickenIngredient.setUnit(Unit.ST);
        Instruction marinate = new Instruction();
        marinate.setInstruction("Marinate the chicken");

        Instruction mixTzatziki = new Instruction();
        mixTzatziki.setInstruction("Mix the tzatziki");

        souvlakiRecipe.setIngredients(List.of(chickenIngredient));
        souvlakiRecipe.setInstructions(List.of(marinate, mixTzatziki));
        recipeRepository.save(souvlakiRecipe);

        log.info("Stored the first entries in the recipe database");

        recipeRepository.findAll().forEach(repo -> log.info("Recipes {}-{}", repo.getId(), repo.getDescription()));

    }

}
