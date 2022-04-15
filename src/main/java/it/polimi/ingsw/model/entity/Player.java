package it.polimi.ingsw.model.entity;

import it.polimi.ingsw.model.enumeration.Card;
import it.polimi.ingsw.model.enumeration.Wizard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {

    private String name;
    private School school;
    private List<Card> handCards;
    private List<Card> usedCards;
    private int coins;
    private Wizard wizard;

    public Player() {
        usedCards = new ArrayList<>();
        handCards = new ArrayList<Card>(Arrays.asList(Card.values()));
        coins = 0;
    }

    public Player(String name, Wizard wizard, School school, int coins) {
        this();
        this.wizard = wizard;
        this.name = name;
        this.school = school;
        this.coins = coins;
    }

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

    public List<Card> getHandCards() {
        return handCards;
    }

    public Card getLastPlayedCard() {
        return usedCards.size() != 0 ? usedCards.get(usedCards.size() - 1) : null;
    }

    public String getName() {
        return name;
    }
}
