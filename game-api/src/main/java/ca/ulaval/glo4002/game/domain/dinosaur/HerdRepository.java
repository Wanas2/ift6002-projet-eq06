package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.herd.Herd;

import java.util.Optional;

public interface HerdRepository {

    void save(Herd herd);

    Optional<Herd> find();

    void delete();
}