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

    /**
     * Given 2 Islands, unifies them into a single island block and refresh all the references
     * to the old islands, including:
     *  - prevIsland and nextIsland of the communicating Islands
     *  - motherNature island
     *  - game islands list
     *
     * Requires the two islands to be communicating
     *
     * @param island1 The island to unify with island2
     * @param island2 The island to unify with island1
     *
     */
    public void unifyIsland(Island island1, Island island2) {
        for(Student s : island2.getStudents()) {
            island1.addPawn(s);
        }
        for(Tower t : island2.getTowers()) {
            island1.addTower(t);
        }

        if(island1.getNextIsland().equals(island2)) {
            island1.setNextIsland(island2.getNextIsland());
            island2.getNextIsland().setPrevIsland(island1);
        }
        else {
            island1.setPrevIsland(island2.getPrevIsland());
            island2.getPrevIsland().setNextIsland(island1);
        }

        motherNature.setIsland(island1);

        islands.remove(island2);
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
