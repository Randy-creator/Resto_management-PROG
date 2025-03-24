package org.resto.DAO;

import org.resto.Entity.DishOrder;

import java.util.List;

public interface DishOrderCrud {
    public DishOrder createDishOrder(DishOrder toCreate, int orderId);
}
