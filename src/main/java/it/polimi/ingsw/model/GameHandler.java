package it.polimi.ingsw.model;

import java.util.List;

public class GameHandler {

    private Player currentPlayer;
    private GamePhase gamePhase;
    private TurnPhase turnPhase;
    private Player firstTurnPlayer;
    private boolean ended;
    private Game game;

    public void nextGamePhase() {

    }

    public void nextTurnPhase() {

    }

    public void nextPlayer(List<Player> players) {

    }

    public boolean isEnded() {
        return ended;
    }

    void setEnded(boolean ended) {
        this.ended = ended;
    }
}
