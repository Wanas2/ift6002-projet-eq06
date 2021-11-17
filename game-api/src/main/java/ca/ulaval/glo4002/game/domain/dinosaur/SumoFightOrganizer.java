package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.DinosaurAlreadyParticipatingException;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.MaxCombatsReachedException;

import java.util.*;

public class SumoFightOrganizer {

    private final static int MAX_FIGHT = 2;

    private int numberOfFight = 0;
    private final List<Dinosaur> fighters = new ArrayList<>();

    public String sumoFight(Dinosaur challenger, Dinosaur challenged) {
        String result = "";
        if(validateFight() && validateFighter(challenger, challenged)) {
            if(challenger.compareTo(challenged) > 0)
                result = challenger.getName();
            else if(challenger.compareTo(challenged) < 0)
                result = challenged.getName();
            else
                result = "tie";
        }
        numberOfFight++;
        fighters.addAll(Arrays.asList(challenger, challenged));
        return result;
    }

    public void reset() {
        numberOfFight = 0;
        fighters.clear();
    }

    private boolean validateFighter(Dinosaur firstDinosaur, Dinosaur secondDinosaur) {
        if(fighters.contains(firstDinosaur) || fighters.contains(secondDinosaur))
            throw new DinosaurAlreadyParticipatingException();
        return true;
    }

    private boolean validateFight() {
        if(numberOfFight >= MAX_FIGHT) {
            throw new MaxCombatsReachedException();
        }
        return true;
    }
}
