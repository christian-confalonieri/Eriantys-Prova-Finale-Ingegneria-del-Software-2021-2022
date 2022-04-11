package it.polimi.ingsw.controller.services;

import it.polimi.ingsw.action.PlayCardAction;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.server.Server;

public class GameService {
    public static void playCard(PlayCardAction action) {
        GameHandler gameHandler = Server.getInstance().getGameHandler(action.getPlayerId());

        // if (Nessuno prima di me ha giocato questa carta o non c'è alternativa
        gameHandler.getCurrentPlayer().playCard(action.getPlayedCard());
    }
}
