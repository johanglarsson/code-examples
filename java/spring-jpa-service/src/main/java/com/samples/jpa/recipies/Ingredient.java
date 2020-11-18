package com.samples.jpa.recipies;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Ingredient {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int amount;

    @Getter
    @Setter
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Recipe recipe;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Ingredient other = (Ingredient) obj;
        if (id == null) {
            return false;
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;

    }

}
