package ca.ulaval.glo4002.game.domain.food;

import java.util.HashMap;
import java.util.Map;

public class CookItSubscription {

    private final int BURGER_QUANTITY_FOR_A_TURN = 100;
    private final int SALAD_QUANTITY_FOR_A_TURN = 250;
    private final int WATER_QUANTITY_IN_LITERS_FOR_A_TURN = 10000;

    private Map<FoodType, Food> foodForOneTurn;

    public void provideFood() {
        foodForOneTurn = new HashMap<>();
        Food defaultBurgers = new Food(FoodType.BURGER, BURGER_QUANTITY_FOR_A_TURN);
        Food defaultSalads = new Food(FoodType.SALAD, SALAD_QUANTITY_FOR_A_TURN);
        Food defaultWater = new Food(FoodType.WATER, WATER_QUANTITY_IN_LITERS_FOR_A_TURN);

        foodForOneTurn.put(FoodType.BURGER, defaultBurgers);
        foodForOneTurn.put(FoodType.SALAD, defaultSalads);
        foodForOneTurn.put(FoodType.WATER, defaultWater);
    }
}
