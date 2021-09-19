package ca.ulaval.glo4002.game.domain.parkResources;

public class Food {

    private String id;
    private FoodType type;
    int currentAgeInNumberOfTurns = 0;

    public Food(String id, FoodType type) {
        this.id = id;
        this.type = type;
    }

    void updateCurrentAge() {
        currentAgeInNumberOfTurns += 1;
    }

    boolean isExpired() {
        return currentAgeInNumberOfTurns >= FoodType.BURGER.getNumberOfTurnBeforeExpiry();
    }

    public String getId() {
        return id;
    }
}
