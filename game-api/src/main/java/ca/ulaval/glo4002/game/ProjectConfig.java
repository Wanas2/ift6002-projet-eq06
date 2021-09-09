package ca.ulaval.glo4002.game;

import ca.ulaval.glo4002.game.applicationService.GameService;
import ca.ulaval.glo4002.game.applicationService.TurnAssembler;
import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.TurnFactory;
import ca.ulaval.glo4002.game.interfaces.rest.game.GameResource;
import ca.ulaval.glo4002.game.interfaces.rest.heartbeat.HeartbeatResource;
import org.glassfish.jersey.server.ResourceConfig;

public class ProjectConfig extends ResourceConfig {

    public ProjectConfig() {
        registerResources();
    }

    private void registerResources() {
        TurnFactory turnFactory = new TurnFactory();
        Game game = new Game(turnFactory);

        // Todo Initialiser les assemblers ici
        TurnAssembler turnAssembler = new TurnAssembler();

        // Todo Initialiser les services ici
        GameService gameService = new GameService(turnAssembler, game);

        // Todo Initialiser les ressources ici
        HeartbeatResource heartbeatResource = new HeartbeatResource();
        GameResource gameResource = new GameResource(gameService);

        // Todo Enregistrer les ressources ici
        register(heartbeatResource);
        register(gameResource);
    }
}
