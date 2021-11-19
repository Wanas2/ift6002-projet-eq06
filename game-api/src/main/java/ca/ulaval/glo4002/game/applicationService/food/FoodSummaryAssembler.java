package ca.ulaval.glo4002.game.applicationService.food;

import ca.ulaval.glo4002.game.domain.food.FoodHistory;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodSummaryDTO;

public class FoodSummaryAssembler {

    private final FoodAssembler foodAssembler;

    public FoodSummaryAssembler(FoodAssembler foodAssembler) {
        this.foodAssembler = foodAssembler;
    }

    public FoodSummaryDTO toDTO(FoodHistory foodHistory) {
        FoodSummaryDTO foodSummaryDTO = new FoodSummaryDTO();
        foodSummaryDTO.fresh = foodAssembler.toDTO(foodHistory.getFreshFoodQuantities());
        foodSummaryDTO.expired = foodAssembler.toDTO(foodHistory.getExpiredFoodQuantities());
        foodSummaryDTO.consumed = foodAssembler.toDTO(foodHistory.getConsumedFoodQuantities());
        return foodSummaryDTO;
    }
}
