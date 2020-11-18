package com.samples.jpa.recipies;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseLoader implements CommandLineRunner {

    private final RecipeRepository recipeRepository;

    private final IngredientRepository ingredientRepository;

    private final InstructionRepository instructionRepository;

    @Override
    public void run(String... args) throws Exception {

        Recipe souvlakiRecipe = new Recipe();
        souvlakiRecipe.setDescription("Chicken souvlaki");

        Ingredient chickenIngredient = new Ingredient();
        chickenIngredient.setAmount(1);
        chickenIngredient.setName("Chicken");
        chickenIngredient.setUnit(Unit.ST);
        chickenIngredient.setRecipe(souvlakiRecipe);
        Instruction marinate = new Instruction();
        marinate.setInstruction("Marinate the chicken");
        marinate.setRecipe(souvlakiRecipe);
        Instruction mixTzatziki = new Instruction();
        mixTzatziki.setInstruction("Mix the tzatziki");
        mixTzatziki.setRecipe(souvlakiRecipe);

        recipeRepository.save(souvlakiRecipe);
        ingredientRepository.save(chickenIngredient);
        instructionRepository.save(mixTzatziki);
        instructionRepository.save(marinate);

        log.info("Stored the first entries in the recipe database");

        recipeRepository.findAll().forEach(repo -> log.info("Recipes {}-{}", repo.getId(), repo.getDescription()));

    }

}
