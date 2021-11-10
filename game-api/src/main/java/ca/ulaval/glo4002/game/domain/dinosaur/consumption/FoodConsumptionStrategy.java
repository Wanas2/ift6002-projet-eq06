package ca.ulaval.glo4002.game.domain.dinosaur.consumption;

import java.util.List;

public interface FoodConsumptionStrategy {

    List<FoodNeed> getFoodNeeds(int weight, int age);

    boolean areFoodNeedsSatisfied();
}
