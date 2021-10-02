package ca.ulaval.glo4002.game.domain.dinosaur.consumption;

public interface FoodConsumptionStrategy {
    boolean consumeFood(int weight, int age);
}
