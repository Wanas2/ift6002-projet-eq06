package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.*;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.InvalidGenderException;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.InvalidSpeciesException;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.InvalidWeightException;

public class DinosaurFactory {

    class GeneralDinosaurState{
        public Species species;
        public Gender gender;
        public FoodConsumptionStrategy strategy;

        public GeneralDinosaurState(Species species, Gender gender, FoodConsumptionStrategy strategy) {
            this.species = species;
            this.gender = gender;
            this.strategy = strategy;
        }
    }

    private CarnivorousFoodStorage carnivorousFoodStorage;
    private HerbivorousFoodStorage herbivorousFoodStorage;

    public DinosaurFactory(CarnivorousFoodStorage carnivorousFoodStorage,
                           HerbivorousFoodStorage herbivorousFoodStorage) {
        this.carnivorousFoodStorage = carnivorousFoodStorage;
        this.herbivorousFoodStorage = herbivorousFoodStorage;
    }

    private GeneralDinosaurState createGeneralDinosaurState(String genderName, String speciesName){
        if (!genderName.equals("m") && !genderName.equals("f")){
            throw new InvalidGenderException();
        }
        Gender gender = Gender.valueOf(genderName.toUpperCase());

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

        return new GeneralDinosaurState(species,gender,foodConsumptionStrategy);
    }

    public Dinosaur create(String genderName, int weight, String speciesName, String name){
        if (weight <= 0)
            throw new InvalidWeightException();

        GeneralDinosaurState state = createGeneralDinosaurState(genderName,speciesName);

        return new Dinosaur(state.species,weight, name, state.gender, state.strategy);
    }

    public BabyDinosaur createBaby(String genderName, String speciesName, String name, Dinosaur fatherDinosaur,
                                   Dinosaur motherDinosaur){
        GeneralDinosaurState state = createGeneralDinosaurState(genderName,speciesName);

        return new BabyDinosaur(state.species,name,state.gender,state.strategy,fatherDinosaur,motherDinosaur);
    }
}
