package it.polimi.ingsw.action;

import java.util.List;

public class GameInterruptedAction extends Action {
    private final List<String> leaderBoard;


    public GameInterruptedAction(String playerId, List<String> leaderBoard) {
        super(ActionType.GAMEINTERRUPTED, playerId);
        this.leaderBoard = leaderBoard;
    }

    public List<String> getLeaderBoard() {
        return leaderBoard;
    }
}
