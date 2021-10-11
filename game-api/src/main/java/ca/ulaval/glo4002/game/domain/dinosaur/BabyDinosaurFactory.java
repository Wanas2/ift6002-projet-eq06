package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.*;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.InvalidGenderException;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.InvalidSpeciesException;

//FIXME Duplication entre BebeDinosaurFactory et DinosaurFactory
public class BabyDinosaurFactory {
    private CarnivorousFoodStorage carnivorousFoodStorage;
    private HerbivorousFoodStorage herbivorousFoodStorage;

    public BabyDinosaurFactory(CarnivorousFoodStorage carnivorousFoodStorage,
                               HerbivorousFoodStorage herbivorousFoodStorage) {
        this.carnivorousFoodStorage = carnivorousFoodStorage;
        this.herbivorousFoodStorage = herbivorousFoodStorage;
    }

    public BabyDinosaur create(String gender, String speciesName, String name,
                               Dinosaur father, Dinosaur mother){
        if (!gender.equals("m") && !gender.equals("f")){
            throw new InvalidGenderException();
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

        return new BabyDinosaur(species, name, Gender.valueOf(gender.toUpperCase()),
                foodConsumptionStrategy,father,mother);
    }
}
