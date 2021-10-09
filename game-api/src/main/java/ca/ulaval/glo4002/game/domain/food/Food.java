package ca.ulaval.glo4002.game.domain.food;

public class Food {

    private final FoodType type;
    private int quantity;
    private int age = 0;

    public Food(FoodType type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public void increaseQuantity(Food food) throws FoodTypeNotMatchingException {
        if((food.type).equals(type))
            quantity += food.quantity;
        else
            throw new FoodTypeNotMatchingException("The Two FoodTypes to be added together must match");
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void incrementAgeByOne() {
        age += 1;
    }

    public void decreaseQuantity(int quantity) {
        if(quantity > this.quantity) {
            this.quantity = 0;
            return;
        }
        this.quantity -= quantity;
    }

    public boolean isExpired() {
        return age >= type.numberOfTurnBeforeExpiry();
    }

    public int quantity() {
        return quantity;
    }
}
