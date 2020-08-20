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
public class Instruction {

    @Id
    @GeneratedValue
    private Long id;

    private String instruction;

    public Instruction(final String instruction) {
        this.instruction = instruction;
    }

}
