package it.polimi.ingsw.action;

public class LogoutAction extends Action {
    public LogoutAction(String playerId) {
        super(ActionType.LOGOUT, playerId);
    }
}
