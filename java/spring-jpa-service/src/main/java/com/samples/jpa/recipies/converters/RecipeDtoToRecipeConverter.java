package com.samples.jpa.recipies.converters;

import java.util.stream.Collectors;

import com.samples.jpa.recipies.Ingredient;
import com.samples.jpa.recipies.Instruction;
import com.samples.jpa.recipies.Recipe;
import com.samples.jpa.recipies.dtos.IngredientDto;
import com.samples.jpa.recipies.dtos.InstructionDto;
import com.samples.jpa.recipies.dtos.RecipeDto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeDtoToRecipeConverter implements Converter<RecipeDto, Recipe> {

    @Override
    public Recipe convert(RecipeDto source) {
        Recipe recipe = new Recipe();
        recipe.setDescription(source.getDescription());
        recipe.setIngredients(source.getIngredients().stream().map(this::toIncredient).collect(Collectors.toSet()));
        recipe.setInstructions(source.getInstructions().stream().map(this::toInstruction).collect(Collectors.toSet()));
        return recipe;
    }

    private Ingredient toIncredient(IngredientDto dto) {
        Ingredient ingredient = new Ingredient();
        ingredient.setAmount(dto.getAmount());
        ingredient.setName(dto.getName());
        ingredient.setUnit(dto.getUnit());
        return ingredient;

    }

    private Instruction toInstruction(InstructionDto dto) {
        Instruction instruction = new Instruction();
        instruction.setInstruction(dto.getInstruction());
        return instruction;
    }

}