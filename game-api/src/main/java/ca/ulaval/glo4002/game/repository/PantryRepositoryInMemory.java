package ca.ulaval.glo4002.game.repository;

import ca.ulaval.glo4002.game.domain.parkResources.Food;
import ca.ulaval.glo4002.game.domain.parkResources.PantryRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PantryRepositoryInMemory implements PantryRepository {

    private Map<String, Food> foods = new HashMap<>(); // Todo Refactor

    @Override
    public void save(List<Food> foods) {
        for(Food food: foods) {
            this.foods.put(food.getId(), food);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public boolean remove(String id) {
        return false;
    }
}
