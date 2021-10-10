package ca.ulaval.glo4002.game.interfaces.rest.food;

import ca.ulaval.glo4002.game.applicationService.GameService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/resources")
public class FoodResource {

    private final GameService gameService;
    private final FoodValidator foodValidator;

    public FoodResource(GameService gameService, FoodValidator foodValidator) {
        this.gameService = gameService;
        this.foodValidator = foodValidator;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addFood(FoodDTO foodDTO) {
        foodValidator.validateFoodEntries(foodDTO);
        gameService.addFood(foodDTO);
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFoodQuantitySummary() {
        FoodSummaryDTO foodSummaryDTO = gameService.getFoodQuantitySummary();
        return Response.ok().entity(foodSummaryDTO).build();
    }
}
