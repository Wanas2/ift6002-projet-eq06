package ca.ulaval.glo4002.game.interfaces.rest.food;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class FoodResourceTest {

    private FoodResource foodResource;

    @BeforeEach
    void setUp() {
        foodResource = new FoodResource();
    }

    @Test
    public void whenOrderFood_thenShouldReturnAppropriateResponse() {
        Response expectedResponse = Response.status(200).build();

        foodResource.orderFood();

//        assertEquals(expectedResponse, re); // Todo J'arr
    }
}
