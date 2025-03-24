package org.resto.DAO;

import org.resto.Entity.Movement;

import java.util.List;

public interface MovementCrud {
    public List<Movement> getMovementList(int page, int size);

    public void createMovement(Movement movement);
}
