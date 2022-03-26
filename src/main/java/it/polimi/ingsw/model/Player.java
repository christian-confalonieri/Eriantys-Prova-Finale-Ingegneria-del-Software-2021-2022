package it.polimi.ingsw.model;

import java.util.List;

public class Player {

    private String name;
    private School school;
    private List<Card> handCards;
    private List<Card> usedCards;
    private int coins;

    /**
     * This method moves the card received as the parameter from the deck to the waste deck.
     *
     * @param card the card to be moved from handCards to usedCards
     */
    public void playCard(Card card) {

        // The card specified as the parameter is removed from the deck
        boolean flag1 = this.handCards.remove(card);

        // If card was found in handCards
        if ( flag1 ) {

            // The card specified as the parameter is placed into the "waste deck"
            this.usedCards.add(card);

        }

    }

    public School getSchool() {
        return school;
    }
}
