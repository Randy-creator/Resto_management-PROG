package org.resto.DAO;

import org.resto.Entity.State;

import java.util.List;

public interface StateCrud {
    public State insertState(State toCreate, int orderID);

    public List<State> getAllStates(int orderID);
}