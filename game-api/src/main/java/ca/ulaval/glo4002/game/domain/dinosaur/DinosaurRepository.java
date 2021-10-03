package ca.ulaval.glo4002.game.domain.dinosaur;

import java.util.List;

public interface DinosaurRepository {
    boolean existsByName(String name);
    Dinosaur findByName(String name);
    void deleteAll();
    List<Dinosaur> findAll();
    void syncAll(List<Dinosaur> dinosaurs);
}
