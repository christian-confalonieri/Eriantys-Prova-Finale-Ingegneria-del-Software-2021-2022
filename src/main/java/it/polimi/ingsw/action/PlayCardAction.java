package it.polimi.ingsw.action;

import it.polimi.ingsw.exceptions.InvalidAction;
import it.polimi.ingsw.model.enumeration.Card;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.server.Server;

public class PlayCardAction extends PlayAction {
    protected Card playedCard;

    public PlayCardAction(String playerId) {
        super(ActionType.PLAYCARD, playerId);
    }

    @Override
    public void execute() throws InvalidAction {
        super.execute();
        GameHandler gameHandler = Server.getInstance().getGameHandler(playerId);

        // if (Nessuno prima di me ha giocato questa carta o non c'è alternativa
        gameHandler.getCurrentPlayer().playCard(playedCard);
    }
}
