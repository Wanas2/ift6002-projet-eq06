package ca.ulaval.glo4002.game.domain;

import java.util.Queue;

public interface Playable {

    int play();

    void reset();

    boolean hasActions();

    void addAction(Action action);
}
