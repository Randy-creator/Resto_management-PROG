package org.resto.DAO;

import org.resto.Entity.Dish;

import java.util.List;

public interface DishCrud {
    public List<Dish> getAllDishes(int page, int size);
}
