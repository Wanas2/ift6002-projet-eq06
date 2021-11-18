package ca.ulaval.glo4002.game.domain.dinosaur;

import java.util.*;

public class SumoFightOrganizer {

    private final static int MAX_NUMBER_OF_FIGHTS_PER_TURN = 2;

    private int numberOfFightDone = 0;
    private final List<Dinosaur> dinosaursAlreadyFought = new ArrayList<>();
    private final SumoFightOrganizerValidator sumoFightOrganizerValidator;

    public SumoFightOrganizer(SumoFightOrganizerValidator sumoFightOrganizerValidator) {
        this.sumoFightOrganizerValidator = sumoFightOrganizerValidator;
    }

    public String sumoFight(Dinosaur challenger, Dinosaur challenged) {
        sumoFightOrganizerValidator.validateSumoFight(numberOfFightDone, MAX_NUMBER_OF_FIGHTS_PER_TURN);
        sumoFightOrganizerValidator.validateSumoFighter(dinosaursAlreadyFought, challenger, challenged);
        numberOfFightDone++;
        dinosaursAlreadyFought.addAll(Arrays.asList(challenger, challenged));
        if(challenger.compareStrength(challenged) > 0) {
            return challenger.getName();
        } else if(challenger.compareStrength(challenged) < 0) {
            return challenged.getName();
        }

        return "tie";
    }

    public void reset() {
        numberOfFightDone = 0;
        dinosaursAlreadyFought.clear();
    }
}
