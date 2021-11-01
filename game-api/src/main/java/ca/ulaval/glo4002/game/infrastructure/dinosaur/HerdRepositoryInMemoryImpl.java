package ca.ulaval.glo4002.game.infrastructure.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.Herd;
import ca.ulaval.glo4002.game.domain.dinosaur.HerdRepository;

import java.util.Optional;

public class HerdRepositoryInMemoryImpl implements HerdRepository {

    private Herd herd;

    @Override
    public void save(Herd herd) {
        this.herd = herd;
    }

    @Override
    public Optional<Herd> find() {
        return Optional.ofNullable(herd);
    }

    @Override
    public void delete() {
        herd = null;
    }
}
