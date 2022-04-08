package it.polimi.ingsw.action;

import it.polimi.ingsw.exceptions.InvalidAction;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.server.Server;

public class PlayAction extends Action {

    @Override
    public void execute() throws InvalidAction {
        super.execute();
        GameHandler gameHandler = Server.getInstance().getGameHandler(playerId);
        if (!this.playerId.equals(gameHandler.getCurrentPlayer().getName())) {
            // Action refused
            throw new InvalidAction("Action sent by not the current player");
        }
    }


    public PlayAction(ActionType actionType, String playerId) {
        super(actionType, playerId);
    }
}
