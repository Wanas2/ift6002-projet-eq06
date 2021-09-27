package ca.ulaval.glo4002.game.interfaces.rest.food;

import ca.ulaval.glo4002.game.applicationService.food.PantryService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class FoodResource {

    private final PantryService pantryService;
    private final FoodValidator foodValidator;

    public FoodResource(PantryService pantryService, FoodValidator foodValidator) {
        this.pantryService = pantryService;
        this.foodValidator = foodValidator;
    }

    @POST
    @Path("/resources")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response orderFood(FoodDTO foodDTO) {
        foodValidator.validateFoodEntries(foodDTO);
        pantryService.orderFood(foodDTO);
        return Response.ok().build();
    }
}
