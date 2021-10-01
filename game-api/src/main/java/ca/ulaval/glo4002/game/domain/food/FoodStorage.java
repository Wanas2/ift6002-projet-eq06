package ca.ulaval.glo4002.game.domain.food;

import java.util.Map;

public interface FoodStorage {

    boolean provideFood(Map<FoodType, Food> allRequestedFood);
}
