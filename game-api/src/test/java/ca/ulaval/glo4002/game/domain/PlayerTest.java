package ca.ulaval.glo4002.game.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @Test
    public void whenPlayTurn_thenAllTurnActionsAreAccumulated() {
        player.playTurn();


    }
}
