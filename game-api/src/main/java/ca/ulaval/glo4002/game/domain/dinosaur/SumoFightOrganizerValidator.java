package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.ArmsTooShortException;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.DinosaurAlreadyParticipatingException;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.MaxCombatsReachedException;

import java.util.List;

public class SumoFightOrganizerValidator {

    public void validateSumoFighters(List<Dinosaur> dinosaursAlreadyFighting, Dinosaur dinosaurChallenger,
                                     Dinosaur dinosaurChallengee) {
        if(dinosaursAlreadyFighting.contains(dinosaurChallenger)
                || dinosaursAlreadyFighting.contains(dinosaurChallengee)){
            throw new DinosaurAlreadyParticipatingException();
        }
        if (dinosaurChallenger.getSpecies() == Species.TyrannosaurusRex
                || dinosaurChallengee.getSpecies() == Species.TyrannosaurusRex){
            throw new ArmsTooShortException();
        }
    }

    public void validateSumoFight(int numberOfFightDone, int maxFightToDo) {
        if(numberOfFightDone >= maxFightToDo){
            throw new MaxCombatsReachedException();
        }
    }
}
