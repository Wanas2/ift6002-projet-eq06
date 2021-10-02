package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.interfaces.rest.dino.DinosaurDTO;
import ca.ulaval.glo4002.game.interfaces.rest.game.TurnNumberDTO;

public class GameService {

    private final TurnAssembler turnAssembler;
    private final DinosaurAssembler dinosaurAssembler;
    private final Game game;

    public GameService(TurnAssembler turnAssembler, DinosaurAssembler dinosaurAssembler, Game game) {
        this.turnAssembler = turnAssembler;
        this.dinosaurAssembler = dinosaurAssembler;
        this.game = game;
    }

    public TurnNumberDTO playTurn() {
        int turnNumber = game.playTurn();

        return turnAssembler.assembleTurnNumber(turnNumber);
    }

    public void addDinosaur(DinosaurDTO dinosaurDTO) {
        Dinosaur dinosaur = dinosaurAssembler.create(dinosaurDTO);
        game.addDinosaur(dinosaur);
    }

    public void reset() {
        game.reset();
    }
}
