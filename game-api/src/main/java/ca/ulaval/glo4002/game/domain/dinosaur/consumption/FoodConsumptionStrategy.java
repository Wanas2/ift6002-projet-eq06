package ca.ulaval.glo4002.game.domain.dinosaur.consumption;

import java.util.List;

public interface FoodConsumptionStrategy {

    List<FoodNeed> getNormalFoodNeeds(int weight);

    List<FoodNeed> getStarvingFoodNeeds(int weight);

    boolean areFoodNeedsSatisfied();
}
