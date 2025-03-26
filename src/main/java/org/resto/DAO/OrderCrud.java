package org.resto.DAO;

import org.resto.Entity.Order;

import java.util.List;

public interface OrderCrud {
    public Order crupdateOrder(Order toCreate);

    public void save(Order toSave);
}
