package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.dinosaur.Herd;
import ca.ulaval.glo4002.game.domain.food.*;
import ca.ulaval.glo4002.game.domain.dinosaur.HerdRepository;
import ca.ulaval.glo4002.game.interfaces.rest.game.TurnNumberDTO;

public class GameService {

    private final TurnAssembler turnAssembler;
    private final Game game;
    private final Pantry pantry;
    private final Herd herd;
    private final PantryRepository pantryRepository;
    private final HerdRepository herdRepository;

    public GameService(Game game, Herd herd, Pantry pantry, TurnAssembler turnAssembler,
                       PantryRepository pantryRepository, HerdRepository herdRepository) {
        this.game = game;
        this.herd = herd;
        this.pantry = pantry;
        this.turnAssembler = turnAssembler;
        this.pantryRepository = pantryRepository;
        this.herdRepository = herdRepository;
    }

    public TurnNumberDTO playTurn() {
        int turnNumber = game.playTurn();
        pantryRepository.save(pantry);
        herdRepository.save(herd);
        return turnAssembler.assembleTurnNumber(turnNumber);
    }

    public void reset() {
        game.reset();
        herdRepository.delete();
        pantryRepository.delete();
    }
}
