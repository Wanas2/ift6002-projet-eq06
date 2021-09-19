package ca.ulaval.glo4002.game.domain.parkResources;

import java.util.List;

public interface PantryRepository {

    void save(List<Food> foods);
    void update();
    boolean remove(String id);
}
