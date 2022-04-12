package it.polimi.ingsw.controller.services;

import it.polimi.ingsw.action.PlayCardAction;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.enumeration.GamePhase;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.server.Server;

public class GameService {
    public static void playCard(PlayCardAction action) {
        GameHandler gameHandler = Server.getInstance().getGameHandler(action.getPlayerId());
        Player player = Server.getInstance().getInGamePlayer(action.getPlayerId());

        if (gameHandler.getCurrentPlayer().equals(player) && gameHandler.getGamePhase().equals(GamePhase.PREPARATION)) {
            if(gameHandler.previousPlayedCards().contains(action.getPlayedCard())) {
                if(gameHandler.previousPlayedCards().containsAll(gameHandler.getCurrentPlayer().getHandCards())) { // all your cards are already played: no alternative
                    gameHandler.getCurrentPlayer().playCard(action.getPlayedCard());

                    // TODO send changes to all players
                }
                else { // invalid card (you can play other valid cards)
                    // TODO Ignore and communicate invalid card
                }
            }
            else { // valid card (nobody played this card in this turn)
                gameHandler.getCurrentPlayer().playCard(action.getPlayedCard());

                // TODO send changes to all players
            }
        }

    }

    

}
