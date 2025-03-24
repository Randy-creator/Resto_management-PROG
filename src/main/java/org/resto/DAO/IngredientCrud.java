package org.resto.DAO;

import org.resto.Entity.Ingredient;

import java.util.List;

public interface IngredientCrud {
    public List<Ingredient> getAllIngredients(int page, int size);

    public void createUpdateIngredient(Ingredient ingredient);
}
