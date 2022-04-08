package it.polimi.ingsw.action;

import it.polimi.ingsw.exceptions.InvalidAction;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.server.Server;

public class PlayAction extends Action {

    GameHandler gameHandler;

    @Override
    public void execute() throws InvalidAction {
        super.execute();
        if (!this.playerId.equals(gameHandler.getCurrentPlayer().getName())) {
            // Action refused
            throw new InvalidAction("Action sent by not the current player");
        }
    }

    @Override
    public void init(Server serverApp) {
        super.init(serverApp);
        gameHandler = serverApp.getGameHandler(this.playerId);
    }
}
