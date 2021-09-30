package ca.ulaval.glo4002.game.domain.food;

public class Food {

    private final FoodType type;
    private int quantity;
    private int currentNumberOfTurns = 0;

    public Food(FoodType type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public void increaseQuantity(Food food) {
        quantity += food.quantity;
    }

    public void incrementCurrentNumberOfTurns() {
        currentNumberOfTurns += 1;
    }

    public boolean subtractQuantity(Food food) {
        if(food.quantity > this.quantity) {
            this.quantity = 0;
            return false;
        }
        this.quantity -= food.quantity;
        return false;
    }

    public boolean isExpired() {
        return currentNumberOfTurns > type.numberOfTurnBeforeExpiry();
    }

    public boolean isCompletelyConsumed() {
        return quantity <= 0;
    }

    public int getQuantity() {
        return quantity;
    }
}
