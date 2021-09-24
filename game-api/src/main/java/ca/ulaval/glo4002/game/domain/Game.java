package ca.ulaval.glo4002.game.domain;

import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.domain.turn.Turn;

import java.util.*;

public class Game {

    private final Turn turn;
    private final Pantry pantry;
    private Queue<Map<FoodType, Food>> freshFoodWaitingForPantry = new LinkedList<>();

    public Game(Turn turn, Pantry pantry) {
        this.turn = turn;
        this.pantry = pantry;
    }

    public void orderFood(Map<FoodType, Food> foods) {
        freshFoodWaitingForPantry.add(foods);
    }

    public int playTurn(Map<FoodType, Food> defaultFoodsForATurn) {
        int turnNumber = turn.play();
        pantry.addFood(defaultFoodsForATurn);
        pantry.addFood(freshFoodWaitingForPantry);
        pantry.updateFoodsExpiryDate();
        return turnNumber;
    }

    public void reset() {
        turn.reset();
    }

    public boolean hasFoodWaitingForPantry() {
        return !freshFoodWaitingForPantry.isEmpty();
    }

}
