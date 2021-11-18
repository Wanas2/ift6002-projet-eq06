package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.DinosaurAlreadyParticipatingException;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.MaxCombatsReachedException;

import java.util.List;

public class SumoFightOrganizerValidator {

    public void validateSumoFighter(List<Dinosaur> dinosaursAlreadyFighting, Dinosaur firstFighter, Dinosaur secondFighter) {
        if(dinosaursAlreadyFighting.contains(firstFighter) || dinosaursAlreadyFighting.contains(secondFighter)){
            throw new DinosaurAlreadyParticipatingException();
        }
    }

    public void validateSumoFight(int numberOfFightDone, int maxFightToDo) {
        if(numberOfFightDone >= maxFightToDo){
            throw new MaxCombatsReachedException();
        }
    }
}
