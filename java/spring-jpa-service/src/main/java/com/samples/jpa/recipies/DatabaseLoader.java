package com.samples.jpa.recipies;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.dialect.IngresDialect;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class DatabaseLoader implements CommandLineRunner {

    private final RecipeRepository recipeRepository;

    private final IngredientRepository ingredientRepository;

    private final InstructionRepository instructionRepository;

    @Override
    public void run(String... args) throws Exception {

        Recipe recipe = new Recipe();
        recipe.setDescription("Chicnken souvlaki");
        recipeRepository.save(recipe);

        Ingredient ingredient = new Ingredient();
        ingredient.setAmount("1");
        ingredient.setName("Florsocker");
        ingredient.setUnit("dl");
        ingredient.setRecipe(recipe);
        ingredientRepository.save(ingredient);
        Instruction instruction = new Instruction();
        instruction.setInstruction("Marinate the fucker");
        instruction.setRecipe(recipe);
        instructionRepository.save(instruction);

        Instruction instruction2 = new Instruction();
        instruction2.setInstruction("Then apply the marinade");
        instruction2.setRecipe(recipe);
        instructionRepository.save(instruction2);
        log.info("Saved instructions and ingredients");
        log.info("Stored the first entries in the recipe database");

        recipeRepository.findAll().forEach(repo -> log.info("Recipes {}-{}", repo.getId(), repo.getDescription()));

    }

}
