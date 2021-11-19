package ca.ulaval.glo4002.game.domain.dinosaur.herd;

import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumption;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodNeed;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CarnivorousDinosaurFeeder implements DinosaurFeeder {

    @Override
    public void feedDinosaurs(Map<Dinosaur, List<FoodNeed>> dinosaursWithNeed) {
        List<FoodNeed> carnivorousFoodNeedFromStrongerToWeaker = dinosaursWithNeed.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .flatMap(entry -> entry.getValue().stream())
                .filter(foodNeed -> foodNeed.getFoodConsumption() == FoodConsumption.CARNIVOROUS)
                .collect(Collectors.toList());

        carnivorousFoodNeedFromStrongerToWeaker.forEach(FoodNeed::satisfy);
    }
}
