package org.resto.Entity;

import java.util.List;
import java.util.Objects;

public class DishOrder {
    private int dishOrderId;
    private Dish dishOrdered;
    private double quantityOrdered;
    private List<State> status;

    public DishOrder(int dishOrderId, Dish dishOrdered, double quantityOrdered, List<State> status) {
        this.dishOrderId = dishOrderId;
        this.dishOrdered = dishOrdered;
        this.quantityOrdered = quantityOrdered;
        this.status = status;
    }

    public DishOrder() {
    }

    public int getDishOrderId() {
        return dishOrderId;
    }

    public void setDishOrderId(int dishOrderId) {
        this.dishOrderId = dishOrderId;
    }

    public Dish getDishOrdered() {
        return dishOrdered;
    }

    public void setDishOrdered(Dish dishOrdered) {
        this.dishOrdered = dishOrdered;
    }

    public double getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(double quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public List<State> getStatus() {
        return status;
    }

    public void setStatus(List<State> status) {
        this.status = status;
    }

    private Status getActualStatus() {
        return this.status.getLast().status_name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DishOrder dishOrder = (DishOrder) o;
        return dishOrderId == dishOrder.dishOrderId && Double.compare(quantityOrdered, dishOrder.quantityOrdered) == 0 && Objects.equals(dishOrdered, dishOrder.dishOrdered) && Objects.equals(status, dishOrder.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishOrderId, dishOrdered, quantityOrdered, status);
    }


    @Override
    public String toString() {
        return "DishOrder{" + "dishOrderId=" + dishOrderId + ", dishOrdered=" + dishOrdered + ", quantityOrdered=" + quantityOrdered + ", status=" + status + '}';
    }


    public boolean isAvailable() {
        return this.getQuantityOrdered() <= this.dishOrdered.getAvailableQuantity();
    }
}

