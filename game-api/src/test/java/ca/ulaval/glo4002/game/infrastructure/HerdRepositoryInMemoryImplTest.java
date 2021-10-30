package ca.ulaval.glo4002.game.infrastructure;

import ca.ulaval.glo4002.game.domain.dinosaur.Herd;
import ca.ulaval.glo4002.game.infrastructure.dinosaur.HerdRepositoryInMemoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class HerdRepositoryInMemoryImplTest {

    private final Herd A_HERD = new Herd(new ArrayList<>());
    private HerdRepositoryInMemoryImpl herdRepository;

    @BeforeEach
    public void setup() {
        herdRepository = new HerdRepositoryInMemoryImpl();
    }

    @Test
    public void shouldInitiallyBeEmpty() {
        assertTrue(herdRepository.find().isEmpty());
    }

    @Test
    public void givenAnHerdHasBeenSaved_whenFind_thenItShouldBeFound() {
        herdRepository.save(A_HERD);

        Optional<Herd> foundHerd = herdRepository.find();

        assertTrue(foundHerd.isPresent());
        assertEquals(A_HERD, foundHerd.get());
    }

    @Test
    public void givenAnHerdHasBeenSaved_whenDelete_thenItShouldNotBeFound() {
        herdRepository.save(A_HERD);

        herdRepository.delete();
        Optional<Herd> foundHerd = herdRepository.find();

        assertTrue(foundHerd.isEmpty());
    }
}
