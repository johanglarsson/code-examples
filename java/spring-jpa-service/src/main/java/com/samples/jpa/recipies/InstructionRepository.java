package com.samples.jpa.recipies;

import org.springframework.data.repository.CrudRepository;

public interface InstructionRepository extends CrudRepository<Instruction, Long> {

}