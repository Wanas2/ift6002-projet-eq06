package ca.ulaval.glo4002.game;

import ca.ulaval.glo4002.game.applicationService.food.FoodAssembler;
import ca.ulaval.glo4002.game.applicationService.food.PantryService;
import ca.ulaval.glo4002.game.applicationService.turn.TurnService;
import ca.ulaval.glo4002.game.applicationService.turn.TurnAssembler;
import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodResource;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodValidator;
import ca.ulaval.glo4002.game.interfaces.rest.game.GameResource;
import ca.ulaval.glo4002.game.interfaces.rest.heartbeat.HeartbeatResource;
import org.glassfish.jersey.server.ResourceConfig;

public class ProjectConfig extends ResourceConfig {

    public ProjectConfig() {
        registerResources();
    }

    private void registerResources() {
        Pantry pantry = new Pantry();
        Game game = new Game(pantry);

        FoodValidator foodValidator = new FoodValidator();

        TurnAssembler turnAssembler = new TurnAssembler();
        FoodAssembler foodAssembler = new FoodAssembler();

        TurnService turnService = new TurnService(turnAssembler, game);
        PantryService pantryService = new PantryService(foodAssembler, pantry);

        HeartbeatResource heartbeatResource = new HeartbeatResource();
        GameResource gameResource = new GameResource(turnService);
        FoodResource foodResource = new FoodResource(pantryService, foodValidator);

        register(heartbeatResource);
        register(gameResource);
        register(foodResource);
    }
}
