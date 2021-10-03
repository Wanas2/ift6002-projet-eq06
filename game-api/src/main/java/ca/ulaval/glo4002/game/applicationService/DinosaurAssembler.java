package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.Gender;
import ca.ulaval.glo4002.game.domain.dinosaur.Species;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.*;
import ca.ulaval.glo4002.game.interfaces.rest.dino.DinosaurDTO;

public class DinosaurAssembler {
    private CarnivorousFoodStorage carnivorousFoodStorage;
    private HerbivorousFoodStorage herbivorousFoodStorage;

    public DinosaurAssembler(CarnivorousFoodStorage carnivorousFoodStorage,
                             HerbivorousFoodStorage herbivorousFoodStorage) {
        this.carnivorousFoodStorage = carnivorousFoodStorage;
        this.herbivorousFoodStorage = herbivorousFoodStorage;
    }

    public Dinosaur create(DinosaurDTO dinosaurDTO) {
        /* Attention au cas du Tyrannosaurus Rex pour lequel le nom ne correspond pas exactement
        * à l'enumération (il y a un espace qu'on doit retirer)
        */
        String speciesName = dinosaurDTO.species.replace(" ","");
        Species species = Species.valueOf(speciesName);
        FoodConsumptionStrategy foodConsumptionStrategy;
        switch (species.getConsumptionType()){
            case CARNIVOROUS:
                foodConsumptionStrategy = new CarnivorousFoodConsumptionStrategy(carnivorousFoodStorage);
                break;
            case HERBIVOROUS:
                foodConsumptionStrategy = new HerbivorousFoodConsumptionStrategy(herbivorousFoodStorage);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + species.getConsumptionType());
        }
        return new Dinosaur(species, dinosaurDTO.weight, dinosaurDTO.name,
                Gender.valueOf(dinosaurDTO.gender.toUpperCase()), foodConsumptionStrategy);
    }

    public DinosaurDTO format(Dinosaur dinosaur){
        String gender = dinosaur.getGender().name().toLowerCase();
        String species = dinosaur.getSpecies().getName();
        return new DinosaurDTO(dinosaur.getName(),dinosaur.getWeight(),gender,species);
    }

}
