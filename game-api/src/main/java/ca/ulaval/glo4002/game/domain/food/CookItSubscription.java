package ca.ulaval.glo4002.game.domain.food;

import java.util.HashMap;
import java.util.Map;

public class CookItSubscription {

    private final int BURGER_QUANTITY_FOR_A_TURN = 100;
    private final int SALAD_QUANTITY_FOR_A_TURN = 250;
    private final int WATER_QUANTITY_IN_LITERS_FOR_A_TURN = 10000;

    private final Map<FoodType, Food> food = new HashMap<>();

    public Map<FoodType, Food> provideFood() {
        Food defaultBurgers = new Food(FoodType.BURGER, BURGER_QUANTITY_FOR_A_TURN);
        Food defaultSalads = new Food(FoodType.SALAD, SALAD_QUANTITY_FOR_A_TURN);
        Food defaultWater = new Food(FoodType.WATER, WATER_QUANTITY_IN_LITERS_FOR_A_TURN);

        food.put(FoodType.BURGER, defaultBurgers);
        food.put(FoodType.SALAD, defaultSalads);
        food.put(FoodType.WATER, defaultWater);

        return food;
    }
}
