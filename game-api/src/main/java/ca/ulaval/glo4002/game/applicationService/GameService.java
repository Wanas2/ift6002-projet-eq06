package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.dinosaur.herd.Herd;
import ca.ulaval.glo4002.game.domain.food.*;
import ca.ulaval.glo4002.game.domain.dinosaur.HerdRepository;

public class GameService {

    private final Game game;
    private final Pantry pantry;
    private final Herd herd;
    private final PantryRepository pantryRepository;
    private final HerdRepository herdRepository;

    public GameService(Game game, Herd herd, Pantry pantry, PantryRepository pantryRepository,
                       HerdRepository herdRepository) {
        this.game = game;
        this.herd = herd;
        this.pantry = pantry;
        this.pantryRepository = pantryRepository;
        this.herdRepository = herdRepository;
    }

    public int playTurn() {
        int turnNumber = game.playTurn();
        pantryRepository.save(pantry);
        herdRepository.save(herd);
        return turnNumber;
    }

    public void reset() {
        game.reset();
        herdRepository.delete();
        pantryRepository.delete();
    }
}
