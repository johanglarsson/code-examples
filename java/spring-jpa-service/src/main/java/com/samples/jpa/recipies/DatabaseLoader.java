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

    private final IngredientRepository ingredientRepository;

    private final InstructionRepository instructionRepository;

    @Override
    public void run(String... args) throws Exception {

        Ingredient ingredient = new Ingredient();
        ingredient.setAmount("1");
        ingredient.setName("Florsocker");
        ingredient.setUnit("dl");
        Instruction instruction = new Instruction();
        instruction.setInstruction("Marinate the fucker");

        Instruction instruction2 = new Instruction();
        instruction2.setInstruction("Then apply the marinade");

        Recipe recipe = new Recipe();
        recipe.setDescription("Chicken souvlaki");
        recipe.setIngredients(List.of(ingredient));
        recipe.setInstructions(List.of(instruction, instruction2));
        recipeRepository.save(recipe);

        log.info("Saved instructions and ingredients");
        log.info("Stored the first entries in the recipe database");

        recipeRepository.findAll().forEach(repo -> log.info("Recipes {}-{}", repo.getId(), repo.getDescription()));

    }

}
