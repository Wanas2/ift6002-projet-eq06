package ca.ulaval.glo4002.game;

import ca.ulaval.glo4002.game.applicationService.FoodAssembler;
import ca.ulaval.glo4002.game.applicationService.FoodSummaryAssembler;
import ca.ulaval.glo4002.game.applicationService.GameService;
import ca.ulaval.glo4002.game.applicationService.TurnAssembler;
import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.Turn;
import ca.ulaval.glo4002.game.domain.food.CookItSubscription;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodResource;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodValidator;
import ca.ulaval.glo4002.game.interfaces.rest.game.GameResource;
import ca.ulaval.glo4002.game.interfaces.rest.heartbeat.HeartbeatResource;
import ca.ulaval.glo4002.game.interfaces.rest.mappers.BadRequestExceptionMapper;
import ca.ulaval.glo4002.game.interfaces.rest.mappers.NotFoundExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;

public class ProjectConfig extends ResourceConfig {

    public ProjectConfig() {
        registerResources();
        registerExceptionMappers();
    }

    private void registerResources() {
        CookItSubscription cookItSubscription = new CookItSubscription();
        Turn turn = new Turn();
        Pantry pantry = new Pantry();
        Game game = new Game(pantry, turn, cookItSubscription);

        FoodValidator foodValidator = new FoodValidator();

        TurnAssembler turnAssembler = new TurnAssembler();
        FoodAssembler foodAssembler = new FoodAssembler();
        FoodSummaryAssembler foodSummaryAssembler = new FoodSummaryAssembler();

        GameService gameService = new GameService(game, pantry, turnAssembler, foodAssembler, foodSummaryAssembler);

        HeartbeatResource heartbeatResource = new HeartbeatResource();
        GameResource gameResource = new GameResource(gameService);
        FoodResource foodResource = new FoodResource(gameService, foodValidator);

        register(heartbeatResource);
        register(gameResource);
        register(foodResource);
    }

    private void registerExceptionMappers() {
        register(new BadRequestExceptionMapper());
        register(new NotFoundExceptionMapper());
    }
}
