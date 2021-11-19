package ca.ulaval.glo4002.game.applicationService.food;

import ca.ulaval.glo4002.game.domain.food.FoodState;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodSummaryDTO;

import java.util.Map;

public class FoodSummaryAssembler {

    private final FoodAssembler foodAssembler;

    public FoodSummaryAssembler(FoodAssembler foodAssembler) {
        this.foodAssembler = foodAssembler;
    }

    public FoodSummaryDTO toDTO(Map<FoodState, Map<FoodType, Integer>> allFoodSummary) {
        FoodSummaryDTO foodSummaryDTO = new FoodSummaryDTO(foodAssembler.toDTO(allFoodSummary.get(FoodState.FRESH)), foodAssembler.toDTO(allFoodSummary.get(FoodState.EXPIRED)), foodAssembler.toDTO(allFoodSummary.get(FoodState.CONSUMED)));
        return foodSummaryDTO;
    }
}
