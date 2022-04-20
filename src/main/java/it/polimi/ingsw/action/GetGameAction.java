package it.polimi.ingsw.action;

import it.polimi.ingsw.model.game.GameHandler;

public class GetGameAction extends Action {
    private String gameHandlerInfoJson;

    public GetGameAction(String playerId) {
        super(ActionType.GETGAME, playerId);
    }
    public GetGameAction(String playerId, String gameHandlerInfoJson) {
        super(ActionType.GETGAME, playerId);
        this.gameHandlerInfoJson = gameHandlerInfoJson;
    }

    public String getGameHandlerInfoJson() {
        return gameHandlerInfoJson;
    }
}
