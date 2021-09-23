package ca.ulaval.glo4002.game.domain.dinosaur.consumption;

public interface SaladWaterStorage {
    int giveExactOrMostPossibleWaterDesired(int quantity);
    int giveExactOrMostPossibleSaladDesired(int quantity);
}
