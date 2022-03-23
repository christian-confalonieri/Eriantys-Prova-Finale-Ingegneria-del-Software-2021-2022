package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.IllegalMoveException;

public class MotherNature {

    private Island island;

    /**
     * Construct the MotherNature object initially placed on a island
     * @param island the starting island
     */
    public MotherNature(Island island) {
        this.island = island;
    }

    /**
     * @return the island on which mother nature is on
     */
    public Island isOn() {
        return island;
    }

    /**
     * Moves mother nature n steps on the board
     * @param n number of steps
     * @return the island on which mother nature lands on
     * @throws IllegalMoveException when n <= 0
     */
    public Island move(int n) throws IllegalMoveException {
        if(n <= 0) throw new IllegalMoveException();

        Island landingIsland = island;
        for (int i = 0; i < n; i++) {
            landingIsland = landingIsland.getNextIsland();
        }
        island = landingIsland;
        return island;
    }


    /**
     * @param island
     */
    void setIsland(Island island) {
        this.island = island;
    }
}
