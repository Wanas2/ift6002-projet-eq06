package ca.ulaval.glo4002.game.domain.food;

public class Food {

    private final FoodType type;
    private int quantity;
    private int age = 0;

    public Food(FoodType type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public void increase(Food food) {
        if((food.type).equals(type))
            quantity += food.quantity; // Todo doit-on ajouter un else. comment dire à classe client que ce n'est pas le bon type? Peut-être lancer une exception. Qui va la catch?
    }

    public void incrementAgeByOne() {
        age += 1;
    }

    public boolean decrease(Food food) {
        if(food.quantity > this.quantity) {
            this.quantity = 0;
            return false;
        }
        this.quantity -= food.quantity;
        return true;
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

    public boolean isCompletelyConsumed() {
        return quantity <= 0;
    }

    public int quantity() {
        return quantity;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }
}
