package com.samples.jpa.recipies;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    @Override
    List<Recipe> findAll();
}
