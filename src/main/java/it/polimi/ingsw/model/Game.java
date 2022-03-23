package it.polimi.ingsw.model;

import java.util.List;

public class Game {

    private List<Island> islands;
    private List<Player> players;
    private List<Cloud> clouds;
    private List<Professor> boardProfessors;
    private MotherNature motherNature;
    private Bag bag;
    private List<GameCharacter> characters;
    private int boardNoEntryCards;

    /** Given 2 Islands, unifies them into a single island block and refresh all the references
     * to the old islands, including:
     *  - prevIsland and nextIsland of the communicating Islands
     *  - motherNature island
     *  - game islands list
     *
     * @param island1 The island to unify with island2
     * @param island2 The island to unify with island1
     */
    public void unifyIsland(Island island1, Island island2) {

    }


    /**
     * Routine to check and move the professors according to the game rules.
     */
    public void professorRelocate() {

    }

    /**
     * Routine to refill all the board clouds with students picked from the bag
     */
    public void refillClouds() {

    }

    /**
     * Check if the game is in an end situation and update the gameState if so
     * @return The leaderboard if the game has ended. Otherwise return null.
     */
    public List<Player> checkEndGame() {

    }

}
