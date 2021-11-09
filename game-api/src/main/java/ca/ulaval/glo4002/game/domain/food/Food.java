package ca.ulaval.glo4002.game.domain.food;

public class Food {

    private FoodState foodState = FoodState.FRESH;
    private int age = 0;
    private final FoodType type;
    private int quantity;

    public Food(FoodType type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public void increaseQuantity(Food food) throws FoodTypesNotMatchingException {
        if((food.type).equals(type))
            quantity += food.quantity;
        else
            throw new FoodTypesNotMatchingException("Trying to add two foods whose types are different");
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void incrementAgeByOne() {
        if(age < type.numberOfTurnBeforeExpiry()) {
            age += 1;
        } else {
            foodState = FoodState.EXPIRED;
        }
    }

    public void decreaseQuantity(int quantity) {
        if(quantity > this.quantity) {
            this.quantity = 0;
            return;
        }
        this.quantity -= quantity;
    }

    public FoodState getState() {
        return foodState;
    }

    public int quantity() {
        return quantity;
    }

    public FoodType getType() {
        return type;
    }
}
