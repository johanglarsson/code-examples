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
public class RecipeToRecipeDtoConverter implements Converter<Recipe, RecipeDto> {

    @Override
    public RecipeDto convert(Recipe source) {
        var recipeDto = new RecipeDto();
        recipeDto.setDescription(source.getDescription());
        recipeDto.setIngredients(
                source.getIngredients().stream().map(this::toIngredientDto).collect(Collectors.toList()));
        recipeDto.setInstructions(
                source.getInstructions().stream().map(this::toInstructionDto).collect(Collectors.toList()));
        return recipeDto;
    }

    private IngredientDto toIngredientDto(final Ingredient ingredient) {
        var ingredientDto = new IngredientDto();
        ingredientDto.setAmount(ingredient.getAmount());
        ingredientDto.setName(ingredient.getName());
        ingredientDto.setUnit(ingredient.getUnit());
        return ingredientDto;
    }

    private InstructionDto toInstructionDto(final Instruction instruction) {
        var instructionDto = new InstructionDto();
        instructionDto.setInstruction(instruction.getInstruction());
        return instructionDto;
    }

}