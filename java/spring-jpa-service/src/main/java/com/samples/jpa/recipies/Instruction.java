package com.samples.jpa.recipies;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Instruction {

    @Id
    @GeneratedValue
    private Long id;

    private String instruction;

    @ManyToOne
    private Recipe recipe;

    public Instruction(final String instruction) {
        this.instruction = instruction;
    }

}
