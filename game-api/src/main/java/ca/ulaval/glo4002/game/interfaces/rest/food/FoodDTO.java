package ca.ulaval.glo4002.game.interfaces.rest.food;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodDTO {

    public final int qtyBurger;
    public final int qtySalad;
    public final int qtyWater;

    @JsonCreator
    public FoodDTO(@JsonProperty(value = "qtyBurger", required = false) int qtyBurger,
                   @JsonProperty(value = "qtySalad", required = false) int qtySalad,
                   @JsonProperty(value = "qtyWater", required = false) int qtyWater) {
        this.qtyBurger = qtyBurger;
        this.qtySalad = qtySalad;
        this.qtyWater = qtyWater;
    }
}
