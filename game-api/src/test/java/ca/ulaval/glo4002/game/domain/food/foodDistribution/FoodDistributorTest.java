package ca.ulaval.glo4002.game.domain.food.foodDistribution;

import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodHistory;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FoodDistributorTest {

    private static final FoodType A_FOOD_TYPE_TO_PROVIDE = FoodType.BURGER;

    private List<Food> allFreshFoods;
    private FoodHistory foodHistory;
    private FoodDistributor foodDistributor;

    @BeforeEach
    void setUp() {
        allFreshFoods = new LinkedList<>();
        foodHistory = new FoodHistory();
        foodDistributor = new FoodDistributor();
    }

    public void givenARequestedFoodType_whenDistributeExactOrMostPossibleFoodAsked_thenTheRestedFoodTypeIsGiven() {

    }

    @Test
    public void givenZeroFoodQuantityRequested_whenDistributeExactOrMostPossibleFoodAsked_thenNoZeroIsGiven() {
        int requestedFoodQuantity = 0;
        int expectedGivenFoodQuantity = 0;

        int totalFoodGivenQuantity = foodDistributor.distributeExactOrMostPossibleFoodAsked(A_FOOD_TYPE_TO_PROVIDE,
                allFreshFoods, requestedFoodQuantity, foodHistory);

        assertEquals(expectedGivenFoodQuantity, totalFoodGivenQuantity);
    }

    @Test
    public void givenQuantityOfFoodRequestedIsLessThenOrEqualToThatAvailableOfFreshFoods_whenDistributeExactOrMostPossibleFoodAsked_thenQuantityRequestedIsGiven() {
        int requestedFoodQuantity = 5;
        int quantityOfAvailableFreshFood = 10;

        Food food = new Food(A_FOOD_TYPE_TO_PROVIDE, quantityOfAvailableFreshFood);
        allFreshFoods.add(food);
        int totalFoodGivenQuantity = foodDistributor.distributeExactOrMostPossibleFoodAsked(A_FOOD_TYPE_TO_PROVIDE,
                allFreshFoods, requestedFoodQuantity, foodHistory);


        assertEquals(requestedFoodQuantity, totalFoodGivenQuantity);
    }

    @Test
    public void givenQuantityOfFoodRequestedIsGreaterThenThatOfAvailableFreshFoods_whenDistributeExactOrMostPossibleFoodAsked_thenQuantityOfAvailableFreshFoodIsGiven() {
        int requestedFoodQuantity = 10;
        int quantityOfAvailableFreshFood = 5;

        Food food = new Food(A_FOOD_TYPE_TO_PROVIDE, quantityOfAvailableFreshFood);
        allFreshFoods.add(food);
        int totalFoodGivenQuantity = foodDistributor.distributeExactOrMostPossibleFoodAsked(A_FOOD_TYPE_TO_PROVIDE,
                allFreshFoods, requestedFoodQuantity, foodHistory);


        assertEquals(quantityOfAvailableFreshFood, totalFoodGivenQuantity);
    }


}
