package com.samples.jpa.recipies;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Recipe {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Setter
    private String description;

    @OneToMany(mappedBy = "recipe")
    private Set<Instruction> instructions = new HashSet<>();;

    @OneToMany(mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

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
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final Recipe other = (Recipe) obj;
        if (id == null) {
            return false;
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

}
