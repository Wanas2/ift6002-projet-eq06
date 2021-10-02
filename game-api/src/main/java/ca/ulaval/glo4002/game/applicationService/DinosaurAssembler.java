package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.Gender;
import ca.ulaval.glo4002.game.domain.dinosaur.Species;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import ca.ulaval.glo4002.game.interfaces.rest.dino.DinosaurDTO;

public class DinosaurAssembler {

    public Dinosaur create(DinosaurDTO dinosaurDTO) {
        FoodConsumptionStrategy foodConsumptionStrategy;
        Species species = Species.valueOf(dinosaurDTO.species);
        Dinosaur dinosaur = new Dinosaur(Species.valueOf(dinosaurDTO.species), dinosaurDTO.weight, dinosaurDTO.name, Gender.valueOf(dinosaurDTO.gender.toUpperCase()), null);
        return dinosaur;
    }

}
