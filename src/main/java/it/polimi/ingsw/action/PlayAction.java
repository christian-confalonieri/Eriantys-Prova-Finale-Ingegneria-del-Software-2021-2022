package it.polimi.ingsw.action;

import it.polimi.ingsw.exceptions.InvalidAction;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.server.Server;

public class PlayAction extends Action {

    public PlayAction(ActionType actionType, String playerId) {
        super(actionType, playerId);
    }
}
