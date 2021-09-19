package ca.ulaval.glo4002.game.interfaces.rest.game;

public class FoodsDTO {

    public final int qtyBurger;
    public final int qtySalad;
    public final int qtyWater;

    public FoodsDTO(int qtyBurger, int qtySalad, int qtyWater) {
        this.qtyBurger = qtyBurger;
        this.qtySalad = qtySalad;
        this.qtyWater = qtyWater;
    }
}
