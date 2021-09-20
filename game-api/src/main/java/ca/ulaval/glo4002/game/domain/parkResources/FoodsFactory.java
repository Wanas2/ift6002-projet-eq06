package ca.ulaval.glo4002.game.domain.parkResources;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FoodsFactory {

    private final List<Food> foods = new ArrayList<>();

    public List<Food> create(int quantityBurger, int quantitySalad, int quantityWater) {
        createBurgers(quantityBurger);
        createSalads(quantitySalad);
        createWater(quantityWater);
        return foods;
    }

    private void createBurgers(int quantityBurger) {
        for(int burgerCount = 0; burgerCount < quantityBurger; burgerCount++){
            String id = UUID.randomUUID().toString();
            FoodType foodType = FoodType.BURGER;
            foods.add(new Food(id, foodType));
        }
    }

    private void createSalads(int quantitySalad) {
        for(int saladCount = 0; saladCount < quantitySalad; saladCount++){
            String id = UUID.randomUUID().toString();
            FoodType foodType = FoodType.SALAD;
            foods.add(new Food(id, foodType));
        }
    }

    private void createWater(int quantityWater) {
        for(int waterCount = 0; waterCount < quantityWater; waterCount++){
            String id = UUID.randomUUID().toString();
            FoodType foodType = FoodType.WATER;
            foods.add(new Food(id, foodType));
        }
    }
}
