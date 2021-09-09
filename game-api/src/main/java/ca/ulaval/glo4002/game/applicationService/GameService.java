package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.interfaces.rest.game.TurnNumberDTO;

public class GameService {

    private TurnAssembler turnAssembler;
    private Game game;

    public GameService(TurnAssembler turnAssembler, Game game) {
        this.turnAssembler = turnAssembler;
        this.game = game;
    }

    public TurnNumberDTO playTurn() {
        int turnNumber = game.playTurn();
        return turnAssembler.assembleTurnNumber(turnNumber);
    }

    public void reset() {
        game.resetGame();
    }
}
