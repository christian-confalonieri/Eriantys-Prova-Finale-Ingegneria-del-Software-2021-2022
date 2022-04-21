package it.polimi.ingsw.action;

import it.polimi.ingsw.model.enumeration.Card;

public class PlayCardAction extends PlayAction {
    public Card getPlayedCard() {
        return playedCard;
    }

    protected Card playedCard;

    public PlayCardAction(String playerId, Card card) {
        super(ActionType.PLAYCARD, playerId);
        this.playedCard = card;
    }

}
