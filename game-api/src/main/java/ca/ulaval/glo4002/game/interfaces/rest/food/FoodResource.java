package ca.ulaval.glo4002.game.interfaces.rest.food;

import ca.ulaval.glo4002.game.applicationService.food.FoodAssembler;
import ca.ulaval.glo4002.game.applicationService.food.FoodSummaryAssembler;
import ca.ulaval.glo4002.game.applicationService.food.ResourceService;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/resources")
public class FoodResource {

    private final ResourceService resourceService;
    private final FoodValidator foodValidator;
    private final FoodAssembler foodAssembler;
    private final FoodSummaryAssembler foodSummaryAssembler;

    public FoodResource(ResourceService resourceService, FoodValidator foodValidator, FoodAssembler foodAssembler,
                        FoodSummaryAssembler foodSummaryAssembler) {
        this.resourceService = resourceService;
        this.foodValidator = foodValidator;
        this.foodAssembler = foodAssembler;
        this.foodSummaryAssembler = foodSummaryAssembler;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addFood(FoodDTO foodDTO) {
        foodValidator.validateFoodEntries(foodDTO);
        Map<FoodType, Food> food = foodAssembler.fromDTO(foodDTO.qtyBurger, foodDTO.qtySalad, foodDTO.qtyWater);
        resourceService.addFood(food);
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFoodQuantitySummary() {
        Map<String, Map<FoodType, Integer>> allFoodSummary = resourceService.getFoodQuantitySummary();
        FoodSummaryDTO foodSummaryDTO = foodSummaryAssembler.toDTO(allFoodSummary, foodAssembler);
        return Response.ok().entity(foodSummaryDTO).build();
    }
}
