package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.interfaces.rest.game.TurnNumberDTO;

public class TurnAssembler {

    public TurnNumberDTO toDTO(int turnNumber) {
        return new TurnNumberDTO(turnNumber);
    }
}
