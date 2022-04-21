package it.polimi.ingsw.action;

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
