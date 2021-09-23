package ca.ulaval.glo4002.game.domain.dinosaur.consumption;

public interface BurgerWaterStorage {
    int giveExactOrMostPossibleWaterDesired(int quantity);
    int giveExactOrMostPossibleBurgerDesired(int quantity);
}
