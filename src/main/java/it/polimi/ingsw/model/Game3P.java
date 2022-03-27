package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.EmptyBagException;

import java.util.List;

public class Game3P extends Game{

    @Override
    public void refillClouds() {
        for (Cloud cloud : clouds)
            for (int i = 0; i < 4; i++) {
                try {
                    Student s = bag.pickStudent();
                    bag.movePawnTo(cloud, s);
                } catch (EmptyBagException e) {}

            }

    }

    /**
     * Construct and initialize a 3 player game
     * @param playerNames The list of the player names
     */
    public Game3P(List<String> playerNames) {

    }
}
