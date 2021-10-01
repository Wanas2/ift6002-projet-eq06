package ca.ulaval.glo4002.game.interfaces.rest.dino;

import ca.ulaval.glo4002.game.domain.dinosaur.DinosaurRepository;
import ca.ulaval.glo4002.game.domain.dinosaur.Species;
import ca.ulaval.glo4002.game.interfaces.rest.exceptions.GeneralBadRequestException;
import ca.ulaval.glo4002.game.interfaces.rest.exceptions.GeneralNotFoundException;

import java.util.Arrays;

public class DinosaurRequestsValidator {
    private DinosaurRepository dinosaurRepository;

    public DinosaurRequestsValidator(DinosaurRepository dinosaurRepository){
        this.dinosaurRepository = dinosaurRepository;
    }

    public void validateAddRequest(DinosaurDTO dinosaurDTO){
        if (!dinosaurDTO.gender.equals("m") && !dinosaurDTO.gender.equals("f")){
            throw new GeneralBadRequestException("INVALID_GENDER","The specified gender must be \"m\" or \"f\".");
        }
        else if (dinosaurDTO.weight < 0){
            throw new GeneralBadRequestException("INVALID_WEIGHT","The specified weight must be greater than 0.");
        }
        else if (Arrays.stream(Species.values())
                .noneMatch(species -> dinosaurDTO.species.equals(species.getName()))){
            throw new GeneralBadRequestException("INVALID_SPECIES","The specified species is not supported.");
        }
        else if (dinosaurRepository.existsByName(dinosaurDTO.name)){
            throw new GeneralBadRequestException("DUPLICATE_NAME",
                    "The specified name already exists and must be unique.");
        }
    }

    public void validateShowRequest(String name){
        if (!dinosaurRepository.existsByName(name)){
            throw new GeneralNotFoundException("NON_EXISTENT_NAME","The specified name does not exist.");
        }
    }
}
