package ca.ulaval.glo4002.game.applicationService.food;

import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodSummaryDTO;

import java.util.Map;

public class FoodSummaryAssembler {

    public FoodSummaryDTO toDTO(Map<String, Map<FoodType, Integer>> allFoodSummary, FoodAssembler foodAssembler) {
        FoodSummaryDTO foodSummaryDTO = new FoodSummaryDTO();
        foodSummaryDTO.fresh =  foodAssembler.createDTO(allFoodSummary.get("fresh"));
        foodSummaryDTO.expired =  foodAssembler.createDTO(allFoodSummary.get("expired"));
        foodSummaryDTO.consumed =  foodAssembler.createDTO(allFoodSummary.get("consumed"));
        return foodSummaryDTO;
    }
}
