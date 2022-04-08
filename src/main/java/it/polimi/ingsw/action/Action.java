package it.polimi.ingsw.action;

import com.google.gson.Gson;
import it.polimi.ingsw.exceptions.InvalidAction;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.server.Server;

public class Action {

    protected String actionType;
    protected String playerId;

    public void execute() throws InvalidAction {}

    public String getActionType() {
        return actionType;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Action(String actionType, String playerId) {
        this.actionType = actionType;
        this.playerId = playerId;
    }
}
