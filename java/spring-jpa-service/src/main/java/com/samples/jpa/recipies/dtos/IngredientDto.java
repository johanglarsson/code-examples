package com.samples.jpa.recipies.dtos;

import com.samples.jpa.recipies.Unit;

import lombok.Data;

@Data
public class IngredientDto {

    private String name;
    private String amount;
    private Unit unit;

}