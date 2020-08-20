package com.samples.jpa.recipies;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Ingredient {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String amount;
    private Unit unit;

    public Ingredient(final String name, final String amount, final Unit unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

}
