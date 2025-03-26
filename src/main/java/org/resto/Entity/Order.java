package org.resto.Entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Order {
    private int orderId;
    private String orderReference;
    private List<DishOrder> dishOrderList;
    private List<State> status;


    public Order(int orderId, String orderReference, List<State> status) {
        this.orderId = orderId;
        this.orderReference = orderReference;
        this.status = status;
    }

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public List<DishOrder> getDishOrderList() {
        return dishOrderList;
    }

    public void setDishOrderList(List<DishOrder> dishOrderList) {
        this.dishOrderList = dishOrderList;
    }

    public List<State> getStatus() {
        return status;
    }

    public void setStatus(List<State> status) {
        this.status = status;
    }

    public String getOrderReference() {
        return orderReference;
    }

    public void setOrderReference(String orderReference) {
        this.orderReference = orderReference;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", dishOrderList=" + dishOrderList +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && Objects.equals(dishOrderList, order.dishOrderList) && Objects.equals(status, order.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, dishOrderList, status);
    }

    public Double getTotalAmount() {
        return this.dishOrderList.stream().
                mapToDouble(x -> x.getDishOrdered().getUnitPrice() * x.getQuantityOrdered())
                .reduce(0.0, Double::sum);
    }


    public Status getActualStatus() {
        return this.status.getLast().status_name;
    }

    public boolean isCommandAvailable() {
        return this.getDishOrderList().stream().allMatch(DishOrder::isAvailable);
    }
}
