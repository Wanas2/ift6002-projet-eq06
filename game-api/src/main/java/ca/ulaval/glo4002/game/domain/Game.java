package ca.ulaval.glo4002.game.domain;

public class Game {

    private Turn turn;

    public Game(TurnFactory turnFactory) {
        turn = turnFactory.createTurn();
    }

    public int playTurn(){
        return turn.play();
    }

    public void resetGame() {
        turn.reset();
    }
}
