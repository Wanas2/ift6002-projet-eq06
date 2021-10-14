package ca.ulaval.glo4002.game.interfaces.rest.food;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FoodValidatorTest {

    private final static int A_NEGATIVE_QUANTITY = -3;
    private final static int A_POSITIVE_QUANTITY_OF_BURGER = 4;
    private final static int A_POSITIVE_QUANTITY_OF_SALAD = 2;
    private final static int A_POSITIVE_QUANTITY_OF_WATER_IN_LITERS = 103;

    private FoodDTO foodDTO;
    private FoodValidator foodValidator;

    @BeforeEach
    void setUp() {
        foodValidator = new FoodValidator();
    }

    @Test
    public void givenANegativeQuantityOfBurger_whenValidateFoodEntries_thenShouldThrowException() {
        initiateFoodWithCorrectQuantity();
        foodDTO.qtyBurger = A_NEGATIVE_QUANTITY;

        assertThrows(InvalidRessourceQuantityException.class, ()->foodValidator.validateFoodEntries(foodDTO));
    }

    @Test
    public void givenANegativeQuantityOfSalad_whenValidateFoodEntries_thenShouldThrowException() {
        initiateFoodWithCorrectQuantity();
        foodDTO.qtySalad = A_NEGATIVE_QUANTITY;

        assertThrows(InvalidRessourceQuantityException.class, ()->foodValidator.validateFoodEntries(foodDTO));
    }

    @Test
    public void givenANegativeQuantityOfWater_whenValidateFoodEntries_thenShouldThrowException() {
        initiateFoodWithCorrectQuantity();
        foodDTO.qtyWater = A_NEGATIVE_QUANTITY;

        assertThrows(InvalidRessourceQuantityException.class, ()->foodValidator.validateFoodEntries(foodDTO));
    }

    @Test
    public void givenCorrectQuantityOfFood_whenValidateFoodEntries_thenShouldNotThrowException() {
        initiateFoodWithCorrectQuantity();

        assertDoesNotThrow(()->foodValidator.validateFoodEntries(foodDTO));
    }

    private void initiateFoodWithCorrectQuantity() {
        foodDTO = new FoodDTO();
        foodDTO.qtyBurger = A_POSITIVE_QUANTITY_OF_BURGER;
        foodDTO.qtySalad = A_POSITIVE_QUANTITY_OF_SALAD;
        foodDTO.qtyWater = A_POSITIVE_QUANTITY_OF_WATER_IN_LITERS;
    }
}