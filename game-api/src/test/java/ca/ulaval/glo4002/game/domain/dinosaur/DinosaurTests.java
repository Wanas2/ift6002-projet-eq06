package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class DinosaurTests {
    Dinosaur A_CARNIVOROUS_DINOSAUR;
    FoodConsumptionStrategy CARNIVOROUS_STRATEGY;
    Dinosaur AN_HERBIVOROUS_DINOSAUR;
    FoodConsumptionStrategy HERBIVOROUS_STRATEGY;
    int ENTRY_TURN = 1;
    int OTHER_TURN = 3;
    int WEIGHT = 81;
    String CARNIVOROUS_NAME = "Bob";
    String HERBIVOROUS_NAME = "Bobi";

    @BeforeEach
    public void setup(){
        CARNIVOROUS_STRATEGY = mock(FoodConsumptionStrategy.class);
        A_CARNIVOROUS_DINOSAUR = new Dinosaur(Species.Spinosaurus,ENTRY_TURN,WEIGHT,CARNIVOROUS_NAME,Gender.M,
                CARNIVOROUS_STRATEGY);
        HERBIVOROUS_STRATEGY = mock(FoodConsumptionStrategy.class);
        AN_HERBIVOROUS_DINOSAUR = new Dinosaur(Species.Ankylosaurus,ENTRY_TURN,WEIGHT,HERBIVOROUS_NAME,Gender.F,
                HERBIVOROUS_STRATEGY);
    }

    @Test
    public void givenANewDinosaur_thenItShouldBeAlive(){
        assertTrue(A_CARNIVOROUS_DINOSAUR.isAlive());
    }

    @Test
    public void givenADinosaur_whenItCanNotEatEnough_thenItShouldDie(){
        when(HERBIVOROUS_STRATEGY.consumeFood(WEIGHT,ENTRY_TURN,OTHER_TURN)).thenReturn(false);

        AN_HERBIVOROUS_DINOSAUR.eat(OTHER_TURN);

        assertFalse(AN_HERBIVOROUS_DINOSAUR.isAlive());
    }

    @Test
    public void givenAnDinosaur_whenItCanEatEnough_thenItShouldStayAlive(){
        when(HERBIVOROUS_STRATEGY.consumeFood(WEIGHT,ENTRY_TURN,OTHER_TURN)).thenReturn(true);

        AN_HERBIVOROUS_DINOSAUR.eat(OTHER_TURN);

        assertTrue(AN_HERBIVOROUS_DINOSAUR.isAlive());
    }
}
