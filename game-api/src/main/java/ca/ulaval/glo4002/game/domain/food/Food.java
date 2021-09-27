package ca.ulaval.glo4002.game.domain.food;

public class Food {

    private FoodType type;
    private int quantity;

    public Food(FoodType type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }
}
