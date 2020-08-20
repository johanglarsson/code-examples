package com.samples.jpa.recipies.dtos;

import lombok.Data;

@Data
public class IngredientDto {

    private String name;
    private String amount;
    private String unit;

}