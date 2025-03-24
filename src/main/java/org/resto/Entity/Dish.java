package org.resto.Entity;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Dish {

    private int dish_id;
    private String name;
    private int unitPrice;
    private List<Ingredient> ingredients;

    public Dish(int dish_id, String name, int unitPrice, List<Ingredient> ingredients) {
        this.dish_id = dish_id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.ingredients = ingredients;
    }

    public Dish() {
    }

    public int getDish_id() {
        return dish_id;
    }

    public void setDish_id(int dish_id) {
        this.dish_id = dish_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }


    public double getIngredientCost() {
        if (ingredients == null || ingredients.isEmpty()) return 0;

        List<Ingredient> sortedIngredientsDesc = ingredients.stream()
                .sorted(Comparator.comparing(Ingredient::getLastModification).reversed())
                .toList();

        LocalDateTime latestModification = sortedIngredientsDesc.get(0).getLastModification();

        List<Ingredient> ingredientsAtDate = ingredients.stream()
                .filter(x -> x.getLastModification().isEqual(latestModification))
                .toList();

        return ingredientsAtDate.stream().mapToDouble(Ingredient::getIngredientCost).sum();
    }


    public double getIngredientCost(LocalDateTime date) {
        if (ingredients == null || ingredients.isEmpty()) return 0;

        List<Ingredient> ingredientsAtDate = ingredients.stream()
                .filter(x -> x.getLastModification().isEqual(date))
                .toList();

        return ingredientsAtDate.stream().mapToDouble(Ingredient::getIngredientCost).sum();
    }


    public long getAvailableQuantity() {
        return this.ingredients.stream().map(ingredient -> Math.round((ingredient.getAvailableQuantity() / ingredient.getQuantity()))).min(Long::compare).get();
    }

    public double getGrossMargin() {
        return this.unitPrice - this.getIngredientCost();
    }

    public double getGrossMargin(LocalDateTime date) {
        return this.unitPrice - this.getIngredientCost(date);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return dish_id == dish.dish_id && unitPrice == dish.unitPrice && Objects.equals(name, dish.name) && Objects.equals(ingredients, dish.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dish_id, name, unitPrice, ingredients);
    }

    @Override
    public String toString() {
        return "Dish{" + "dish_id=" + dish_id + ", name='" + name + '\'' + ", unitPrice=" + unitPrice + ", ingredients=" + ingredients + '}';
    }
}
