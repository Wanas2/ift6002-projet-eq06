package ca.ulaval.glo4002.game.interfaces.rest.food;

public class FoodDTO {

    public final int qtyBurger;
    public final int qtySalad;
    public final int qtyWater;

    public FoodDTO(int qtyBurger, int qtySalad, int qtyWater) {
        this.qtyBurger = qtyBurger;
        this.qtySalad = qtySalad;
        this.qtyWater = qtyWater;
    }
}
