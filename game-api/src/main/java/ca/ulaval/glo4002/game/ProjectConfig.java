package ca.ulaval.glo4002.game;

import ca.ulaval.glo4002.game.applicationService.GameService;
import ca.ulaval.glo4002.game.applicationService.TurnAssembler;
import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.Turn.Turn;
import ca.ulaval.glo4002.game.domain.parkResources.FoodsFactory;
import ca.ulaval.glo4002.game.domain.parkResources.Pantry;
import ca.ulaval.glo4002.game.domain.parkResources.PantryRepository;
import ca.ulaval.glo4002.game.interfaces.rest.game.GameResource;
import ca.ulaval.glo4002.game.interfaces.rest.heartbeat.HeartbeatResource;
import ca.ulaval.glo4002.game.repository.PantryRepositoryInMemory;
import org.glassfish.jersey.server.ResourceConfig;

public class ProjectConfig extends ResourceConfig {

    public ProjectConfig() {
        registerResources();
    }

    private void registerResources() {
        Turn turn = new Turn();
        Pantry pantry = new Pantry();
        Game game = new Game(turn);

        FoodsFactory foodsFactory = new FoodsFactory();
        TurnAssembler turnAssembler = new TurnAssembler();
        PantryRepository pantryRepository = new PantryRepositoryInMemory();

        GameService gameService = new GameService(turnAssembler, foodsFactory, pantryRepository, game, pantry);

        HeartbeatResource heartbeatResource = new HeartbeatResource();
        GameResource gameResource = new GameResource(gameService);

        register(heartbeatResource);
        register(gameResource);
    }
}
