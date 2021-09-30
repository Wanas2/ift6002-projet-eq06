package ca.ulaval.glo4002.game.interfaces.rest.food;

public class FoodValidator {

    public void validateFoodEntries(FoodDTO foodDTO) {
        if(foodDTO.qtyBurger < 0 || foodDTO.qtySalad < 0 || foodDTO.qtyWater < 0 ) // Todo Do we need to write tests for all three?
            throw new InvalidRessourceQuantityException();
    }
}
