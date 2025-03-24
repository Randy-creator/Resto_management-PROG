package org.resto.Entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Ingredient {
    private int ingredient_id;
    private String name;
    private int unitPrice;
    private LocalDateTime lastModification;
    private Unities unity;
    private double quantity;
    private List<Movement> moveHistory;

    public Ingredient(int ingredient_id, String name, int unitPrice, LocalDateTime lastModification, Unities unity, double quantity, List<Movement> moveHistory) {
        this.ingredient_id = ingredient_id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.lastModification = lastModification;
        this.unity = unity;
        this.quantity = quantity;
        this.moveHistory = moveHistory;
    }

    public Ingredient() {
    }

    public int getIngredient_id() {
        return ingredient_id;
    }

    public void setIngredient_id(int ingredient_id) {
        this.ingredient_id = ingredient_id;
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

    public LocalDateTime getLastModification() {
        return lastModification;
    }

    public void setLastModification(LocalDateTime lastModification) {
        this.lastModification = lastModification;
    }

    public Unities getUnity() {
        return unity;
    }

    public void setUnity(Unities unity) {
        this.unity = unity;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public List<Movement> getMoveHistory() {
        return moveHistory;
    }

    public void setMoveHistory(List<Movement> moveHistory) {
        this.moveHistory = moveHistory;
    }

    public double getIngredientCost() {
        return this.quantity * this.unitPrice;
    }

    private Double getTotalStockQuantity(LocalDateTime date) {
        List<Movement> stockByDate = this.moveHistory.stream().filter(x -> x.getStockDateModification().equals(date)).toList();

        double totalQuantity = 0;
        for (Movement move : stockByDate) {
            if (move.isMoveType()) {
                totalQuantity += move.getQuantity();
            } else {
                totalQuantity -= move.getQuantity();
            }
        }
        return totalQuantity;
    }

    private Double getTotalStockQuantity() {
//        List<Movement> sortedMoveByRecentDate = this.moveHistory.stream()
//                .sorted(Comparator.comparing(Movement::getStockDateModification).reversed())
//                .toList();

        List<Movement> stockByDate = this.moveHistory.stream().filter(x -> x.getStockDateModification().equals(LocalDateTime.now())).toList();

        double totalQuantity = 0;
        for (Movement move : stockByDate) {
            if (move.isMoveType()) {
                totalQuantity += move.getQuantity();
            } else {
                totalQuantity -= move.getQuantity();
            }
        }
        return totalQuantity;
    }

    public Double getAvailableQuantity(LocalDateTime dateTime) {
        return this.quantity + getTotalStockQuantity(dateTime == null ? LocalDateTime.now() : dateTime);
    }

    public Double getAvailableQuantity() {
        return this.getQuantity() - (this.getTotalStockQuantity(LocalDateTime.now()));
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return ingredient_id == that.ingredient_id && unitPrice == that.unitPrice && Double.compare(quantity, that.quantity) == 0 && Objects.equals(name, that.name) && Objects.equals(lastModification, that.lastModification) && unity == that.unity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredient_id, name, unitPrice, lastModification, unity, quantity);
    }

    @Override
    public String toString() {
        return "Ingredient{" + "ingredient_id=" + ingredient_id + ", name='" + name + '\'' + ", unitPrice=" + unitPrice + ", lastModification=" + lastModification + ", unity=" + unity + ", quantity=" + quantity + ", moveHistory=" + moveHistory + '}';
    }
}
