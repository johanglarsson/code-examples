package com.samples.jpa.recipies.dtos;

import com.samples.jpa.recipies.Unit;

import lombok.Data;

@Data
public class IngredientDto {

    private String name;
    private int amount;
    private Unit unit;

}