package it.polimi.ingsw.model;

import java.util.List;

public class Player {

    private String name;
    private School school;
    private List<Card> handCards;
    private List<Card> usedCards;
    private int coins;

    public void playCard(Card card) {

    }

    public School getSchool() {
        return school;
    }
}
