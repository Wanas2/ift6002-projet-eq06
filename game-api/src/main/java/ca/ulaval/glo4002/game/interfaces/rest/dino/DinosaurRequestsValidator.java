package ca.ulaval.glo4002.game.interfaces.rest.dino;

import ca.ulaval.glo4002.game.domain.dinosaur.DinosaurRepository;
import ca.ulaval.glo4002.game.domain.dinosaur.Species;
import ca.ulaval.glo4002.game.interfaces.rest.dino.exceptions.*;

import java.util.Arrays;

public class DinosaurRequestsValidator {
    private DinosaurRepository dinosaurRepository;

    public DinosaurRequestsValidator(DinosaurRepository dinosaurRepository){
        this.dinosaurRepository = dinosaurRepository;
    }

    public void validateAddRequest(DinosaurDTO dinosaurDTO){
        if (!dinosaurDTO.gender.equals("m") && !dinosaurDTO.gender.equals("f")){
            throw new InvalidGenderException();
        }
        else if (dinosaurDTO.weight <= 0){
            throw new InvalidWeightException();
        }
        else if (Arrays.stream(Species.values())
                .noneMatch(species -> dinosaurDTO.species.equals(species.getName()))){
            throw new InvalidSpeciesException();
        }
        else if (dinosaurRepository.existsByName(dinosaurDTO.name)){
            throw new DuplicateNameException();
        }
    }

    public void validateShowRequest(String name){
        if (!dinosaurRepository.existsByName(name)){
            throw new NonExistentNameException();
        }
    }
}
