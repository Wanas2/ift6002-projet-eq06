package ca.ulaval.glo4002.game.interfaces.rest.food;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodValidatorTest {

    private final int A_NEGATIVE_QUANTITY_OF_BURGER = -3;
    private final int A_POSITIVE_QUANTITY_OF_SALAD = 2;
    private final int A_POSITIVE_QUANTITY_OF_WATER_IN_LITERS = 103;

    private FoodDTO foodDTO;
    private FoodValidator foodValidator;

    @BeforeEach
    void setUp() {
        foodValidator = new FoodValidator();
    }

    @Test
    public void givenANegativeQuantityOfFood_whenValidateFoodEntries_thenShouldThrowException() {
        initiateFoodWithANegativeQuantity();

        assertThrows(InvalidRessourceQuantityException.class, () -> foodValidator.validateFoodEntries(foodDTO));
    }

    private void initiateFoodWithANegativeQuantity() {
        foodDTO = new FoodDTO();
        foodDTO.qtyBurger = A_NEGATIVE_QUANTITY_OF_BURGER;
        foodDTO.qtySalad = A_POSITIVE_QUANTITY_OF_SALAD;
        foodDTO.qtyWater = A_POSITIVE_QUANTITY_OF_WATER_IN_LITERS;
    }
}