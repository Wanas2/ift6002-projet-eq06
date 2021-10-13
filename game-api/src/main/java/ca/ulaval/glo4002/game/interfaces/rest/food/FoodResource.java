package ca.ulaval.glo4002.game.interfaces.rest.food;

import ca.ulaval.glo4002.game.applicationService.Food.ResourceService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/resources")
public class FoodResource {

    private final ResourceService resourceService;
    private final FoodValidator foodValidator;

    public FoodResource(ResourceService resourceService, FoodValidator foodValidator) {
        this.resourceService = resourceService;
        this.foodValidator = foodValidator;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addFood(FoodDTO foodDTO) {
        foodValidator.validateFoodEntries(foodDTO);
        resourceService.addFood(foodDTO);
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFoodQuantitySummary() {
        FoodSummaryDTO foodSummaryDTO = resourceService.getFoodQuantitySummary();
        return Response.ok().entity(foodSummaryDTO).build();
    }
}
