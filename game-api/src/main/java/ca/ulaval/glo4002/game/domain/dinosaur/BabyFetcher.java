package ca.ulaval.glo4002.game.domain.dinosaur;

public interface BabyFetcher {

    Dinosaur fetch(Dinosaur fatherDinosaur, Dinosaur motherDinosaur, String babyDinoName);
}
