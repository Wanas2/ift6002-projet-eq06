package ca.ulaval.glo4002.game.applicationService.food;

import ca.ulaval.glo4002.game.domain.food.Pantry;

public class PantryService {

    private final FoodAssembler foodAssembler;
    private final Pantry pantry;

    public PantryService(FoodAssembler foodAssembler, Pantry pantry) {
        this.foodAssembler = foodAssembler;
        this.pantry = pantry;
    }

    public void orderFood() {
//        foodAssembler.create(foodDTO); // Todo J'arr
        pantry.orderFood();
    }
}
