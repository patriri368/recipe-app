package com.patrycja.recipeapp.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class CategoryTest {

    Category category;

    @Before
    public void setUp(){
        category = new Category();
    }

    @Test
    public void getId() {

        Long idValue = 4L;

        category.setId(idValue);
        assertEquals(idValue, category.getId());
    }

    @Test
    public void getDescription() {

        String description = "example description";

        category.setDescription(description);
        assertEquals(description, category.getDescription());
    }

    @Test
    public void getRecipes() {

        HashSet recipes = new HashSet();
        Recipe recipe = new Recipe();
        recipes.add(recipe);

        category.setRecipes(recipes);
        assertEquals(1, category.getRecipes().size());
    }
}