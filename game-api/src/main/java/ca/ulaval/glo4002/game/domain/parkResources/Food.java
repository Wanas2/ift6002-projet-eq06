package ca.ulaval.glo4002.game.domain.parkResources;

public class Food {

    private String id;
    private FoodType type;
    private int currentAgeInNumberOfTurns = 0;

    public Food(String id, FoodType type) {
        this.id = id;
        this.type = type;
    }

    public void updateCurrentAge() {
        currentAgeInNumberOfTurns += 1;
    }

    public boolean isExpired() {
        return currentAgeInNumberOfTurns >= type.getNumberOfTurnBeforeExpiry();
    }

    public String getId() {
        return id;
    }
}
