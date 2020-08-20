package com.samples.jpa.recipies.dtos;

import java.util.List;

import lombok.Data;

@Data
public class RecipeDto {

    private String description;

    private List<IngredientDto> ingredients;

    private List<InstructionDto> instructions;

}