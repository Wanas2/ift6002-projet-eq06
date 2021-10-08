package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.*;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.InvalidGenderException;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.InvalidSpeciesException;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.InvalidWeightException;

public class DinosaurFactory {
    private CarnivorousFoodStorage carnivorousFoodStorage;
    private HerbivorousFoodStorage herbivorousFoodStorage;

    public DinosaurFactory(CarnivorousFoodStorage carnivorousFoodStorage,
                           HerbivorousFoodStorage herbivorousFoodStorage) {
        this.carnivorousFoodStorage = carnivorousFoodStorage;
        this.herbivorousFoodStorage = herbivorousFoodStorage;
    }

    public Dinosaur create(String gender, int weight, String speciesName, String name){
        if (!gender.equals("m") && !gender.equals("f")){
            throw new InvalidGenderException();
        }
        else if (weight <= 0){
            throw new InvalidWeightException();
        }

        speciesName = speciesName.replace(" ","");
        Species species;
        try{
            species = Species.valueOf(speciesName);
        } catch (IllegalArgumentException e){
            throw new InvalidSpeciesException();
        }

        FoodConsumptionStrategy foodConsumptionStrategy;
        switch (species.getConsumptionType()){
            case CARNIVOROUS:
                foodConsumptionStrategy = new CarnivorousFoodConsumptionStrategy(carnivorousFoodStorage);
                break;
            case HERBIVOROUS:
                foodConsumptionStrategy = new HerbivorousFoodConsumptionStrategy(herbivorousFoodStorage);
                break;
            default:
                throw new InvalidSpeciesException();
        }

        return new Dinosaur(species,weight, name, Gender.valueOf(gender.toUpperCase()),
                foodConsumptionStrategy);
    }
}
