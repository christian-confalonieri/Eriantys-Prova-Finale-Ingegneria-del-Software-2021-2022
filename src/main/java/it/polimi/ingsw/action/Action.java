package it.polimi.ingsw.action;

public class Action {

    protected ActionType actionType;
    protected String playerId;

    public ActionType getActionType() {
        return actionType;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Action(ActionType actionType, String playerId) {
        this.actionType = actionType;
        this.playerId = playerId;
    }
}
