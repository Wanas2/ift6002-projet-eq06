package ca.ulaval.glo4002.game.domain.dinosaur;

public enum Gender {
    F(1.5f),
    M(1.0f);

    private final float strengthFactor;

    Gender(float strengthFactor) {
        this.strengthFactor = strengthFactor;
    }

    public float getStrengthFactor() {
        return strengthFactor;
    }
}
