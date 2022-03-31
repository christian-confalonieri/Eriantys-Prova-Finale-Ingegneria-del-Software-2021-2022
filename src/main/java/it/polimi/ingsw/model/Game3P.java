package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.EmptyBagException;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class Game3P extends Game{

    /**
     * Construct and initialize a 3 player game
     *
     * @param playersData A map containing the name of the player and the wizard of the player
     * @param gameRules an instance of the gameRules class containing the parameters of the game
     */
    protected Game3P(SortedMap<String, Wizard> playersData, GameRules gameRules) {

    }
}
