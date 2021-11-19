package ca.ulaval.glo4002.game.interfaces.rest.food;

public class FoodDTO {

    public final int qtyBurger;
    public final int qtySalad;
    public final int qtyWater;


    public FoodDTO() {
        this.qtyBurger = 0;
        this.qtySalad = 0;
        this.qtyWater = 0;
    }

    public FoodDTO(int qtyBurger, int qtySalad, int qtyWater) {
        this.qtyBurger = qtyBurger;
        this.qtySalad = qtySalad;
        this.qtyWater = qtyWater;
    }
}
