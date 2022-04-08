package it.polimi.ingsw.action;

import it.polimi.ingsw.exceptions.InvalidAction;
import it.polimi.ingsw.model.enumeration.Card;

public class ActionPlayCard extends PlayAction {
    Card playedCard;

    @Override
    public void execute() throws InvalidAction {
        super.execute();
        // if (Nessuno prima di me ha giocato questa carta o non c'è alternativa
        gameHandler.getCurrentPlayer().playCard(playedCard);
    }
}
