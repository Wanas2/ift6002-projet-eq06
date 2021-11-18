package ca.ulaval.glo4002.game.infrastructure;

import ca.ulaval.glo4002.game.domain.dinosaur.herd.Herd;
import ca.ulaval.glo4002.game.infrastructure.dinosaur.HerdRepositoryInMemoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HerdRepositoryInMemoryImplTest {

    private Herd anHerd;
    private HerdRepositoryInMemoryImpl herdRepository;

    @BeforeEach
    public void setup() {
        anHerd = new Herd(new ArrayList<>(),new ArrayList<>());
        herdRepository = new HerdRepositoryInMemoryImpl();
    }

    @Test
    public void shouldInitiallyBeEmpty() {
        assertTrue(herdRepository.find().isEmpty());
    }

    @Test
    public void givenAnHerdHasBeenSaved_whenFind_thenItShouldBeFound() {
        herdRepository.save(anHerd);

        Optional<Herd> foundHerd = herdRepository.find();

        assertTrue(foundHerd.isPresent());
        assertEquals(anHerd, foundHerd.get());
    }

    @Test
    public void givenAnHerdHasBeenSaved_whenDelete_thenItShouldNotBeFound() {
        herdRepository.save(anHerd);

        herdRepository.delete();

        Optional<Herd> foundHerd = herdRepository.find();
        assertTrue(foundHerd.isEmpty());
    }
}
