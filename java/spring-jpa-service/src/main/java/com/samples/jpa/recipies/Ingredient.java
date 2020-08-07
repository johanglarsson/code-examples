package com.samples.jpa.recipies;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
    private String unit;

    @ManyToOne
    private Recipe recipe;

    public Ingredient(final String name, final String amount, final String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

}
