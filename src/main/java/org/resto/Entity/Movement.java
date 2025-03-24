package org.resto.Entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Movement {
    private int move_id;
    private boolean moveType;
    private double quantity;
    private Unities unity;
    private LocalDateTime stockDateModification;

    public Movement(int move_id, boolean moveType, double quantity, Unities unity, LocalDateTime stockDateModification) {
        this.move_id = move_id;
        this.moveType = moveType;
        this.quantity = quantity;
        this.unity = unity;
        this.stockDateModification = stockDateModification;
    }

    public Movement() {
    }


    public int getMove_id() {
        return move_id;
    }

    public void setMove_id(int move_id) {
        this.move_id = move_id;
    }

    public boolean isMoveType() {
        return moveType;
    }

    public void setMoveType(boolean moveType) {
        this.moveType = moveType;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Unities getUnity() {
        return unity;
    }

    public void setUnity(Unities unity) {
        this.unity = unity;
    }

    public LocalDateTime getStockDateModification() {
        return stockDateModification;
    }

    public void setStockDateModification(LocalDateTime stockDateModification) {
        this.stockDateModification = stockDateModification;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Movement movement = (Movement) o;
        return move_id == movement.move_id && moveType == movement.moveType && Double.compare(quantity, movement.quantity) == 0 && unity == movement.unity && Objects.equals(stockDateModification, movement.stockDateModification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(move_id, moveType, quantity, unity, stockDateModification);
    }

    @Override
    public String toString() {
        return "Movement{" +
                "move_id=" + move_id +
                ", moveType=" + moveType +
                ", quantity=" + quantity +
                ", unity=" + unity +
                ", stockDateModification=" + stockDateModification +
                '}';
    }
}
