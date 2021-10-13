package ca.ulaval.glo4002.game.applicationService.Food;

import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodSummaryDTO;

import java.util.Map;

public class FoodSummaryAssembler {

    public FoodSummaryDTO createDTO(Map<String, Map<FoodType, Integer>> allFoodSummary, FoodAssembler foodAssembler) { // Todo Renommer?
        FoodSummaryDTO foodSummaryDTO = new FoodSummaryDTO();
        foodSummaryDTO.fresh =  foodAssembler.createDTO(allFoodSummary.get("fresh"));
        foodSummaryDTO.expired =  foodAssembler.createDTO(allFoodSummary.get("expired"));
        foodSummaryDTO.consumed =  foodAssembler.createDTO(allFoodSummary.get("consumed"));
        return foodSummaryDTO;
    }
}
