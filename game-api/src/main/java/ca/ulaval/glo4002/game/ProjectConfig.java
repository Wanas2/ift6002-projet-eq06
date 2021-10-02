package ca.ulaval.glo4002.game;

import ca.ulaval.glo4002.game.applicationService.DinosaurAssembler;
import ca.ulaval.glo4002.game.applicationService.GameService;
import ca.ulaval.glo4002.game.applicationService.TurnAssembler;
import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.Turn;
import ca.ulaval.glo4002.game.domain.dinosaur.DinosaurRepositoryImplementation;
import ca.ulaval.glo4002.game.domain.dinosaur.Herd;
import ca.ulaval.glo4002.game.interfaces.rest.dino.DinosaurRequestsValidator;
import ca.ulaval.glo4002.game.interfaces.rest.dino.DinosaurResource;
import ca.ulaval.glo4002.game.interfaces.rest.game.GameResource;
import ca.ulaval.glo4002.game.interfaces.rest.heartbeat.HeartbeatResource;
import ca.ulaval.glo4002.game.interfaces.rest.mappers.BadRequestExceptionMapper;
import ca.ulaval.glo4002.game.interfaces.rest.mappers.NotFoundExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;

public class ProjectConfig extends ResourceConfig {

    public ProjectConfig() {
        registerResources();
    }

    private void registerResources() {
        Turn turn = new Turn();
        Herd herd = new Herd();
        Game game = new Game(turn, herd);

        TurnAssembler turnAssembler = new TurnAssembler();
        DinosaurAssembler dinosaurAssembler = new DinosaurAssembler();
        DinosaurRepositoryImplementation dinosaurRepositoryImplementation = new DinosaurRepositoryImplementation(herd);

        GameService gameService = new GameService(turnAssembler, dinosaurAssembler, game);
        DinosaurRequestsValidator requestValidator = new DinosaurRequestsValidator(dinosaurRepositoryImplementation);

        HeartbeatResource heartbeatResource = new HeartbeatResource();
        GameResource gameResource = new GameResource(gameService);
        DinosaurResource dinosaurResource = new DinosaurResource(gameService, requestValidator);

        register(heartbeatResource);
        register(gameResource);
        register(dinosaurResource);

        register(new BadRequestExceptionMapper());
        register(new NotFoundExceptionMapper());
    }
}
