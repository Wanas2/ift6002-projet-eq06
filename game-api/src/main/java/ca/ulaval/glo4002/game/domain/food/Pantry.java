package ca.ulaval.glo4002.game.domain.food;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Pantry {

    private int MAX_AGE_FOR_BURGERS = 4;

    private Map<Integer, Map<FoodType, Food>> freshFood = new HashMap<>();

    public void addFood(Map<FoodType, Food> foods) {
//        freshFood.get()
//        for ( String key : team1.keySet() ) {
//            System.out.println( key );
//        }

    }

    public void addFood(Queue<Map<FoodType, Food>> foods) {     // Todo À vérifier

    }

    public boolean provideFood() {

        return false;
    }

    public void updateAgeOfFoods() {

    }
}
