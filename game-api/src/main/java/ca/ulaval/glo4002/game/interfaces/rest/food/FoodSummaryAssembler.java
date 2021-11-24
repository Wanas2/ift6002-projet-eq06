package ca.ulaval.glo4002.game.interfaces.rest.food;

import ca.ulaval.glo4002.game.domain.food.FoodState;
import ca.ulaval.glo4002.game.domain.food.FoodType;

import java.util.Map;

public class FoodSummaryAssembler {

    private final FoodAssembler foodAssembler;

    public FoodSummaryAssembler(FoodAssembler foodAssembler) {
        this.foodAssembler = foodAssembler;
    }

    public FoodSummaryDTO toDTO(Map<FoodState, Map<FoodType, Integer>> allFoodSummary) {
        FoodSummaryDTO foodSummaryDTO = new FoodSummaryDTO();
        foodSummaryDTO.fresh = foodAssembler.toDTO(allFoodSummary.get(FoodState.FRESH));
        foodSummaryDTO.expired = foodAssembler.toDTO(allFoodSummary.get(FoodState.EXPIRED));
        foodSummaryDTO.consumed = foodAssembler.toDTO(allFoodSummary.get(FoodState.CONSUMED));
        return foodSummaryDTO;
    }
}
