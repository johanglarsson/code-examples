package com.samples.jpa.recipies;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Unit {
    DL("Deciliter"), ML("Milliliter"), MSK("Matsked"), TSK("Tesked"), L("Liter"), ST("Styck");

    private final String name;
}