package it.polimi.ingsw.action;

public class GetGameAction extends Action {
    public GetGameAction(String playerId) {
        super(ActionType.GETGAME, playerId);
    }
}
