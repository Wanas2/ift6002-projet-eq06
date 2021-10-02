package ca.ulaval.glo4002.game.domain.dinosaur;

public interface DinosaurRepository {
    boolean existsByName(String name);
}
