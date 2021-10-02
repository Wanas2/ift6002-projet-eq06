package ca.ulaval.glo4002.game.interfaces.rest.food;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("positiveValuesFilter")
public class FoodDTO {

    public int qtyBurger = 0;
    public int qtySalad = 0;
    public int qtyWater = 0;
}
