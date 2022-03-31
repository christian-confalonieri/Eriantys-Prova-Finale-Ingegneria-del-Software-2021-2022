package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.exceptions.InvalidNewGameException;

import java.util.ArrayList;
import java.util.List;

public class Game2P extends Game{
    @Override
    public void refillClouds() {
        for (Cloud cloud : clouds)
            for (int i = 0; i < 3; i++) {
                try {
                    Student s = bag.pickStudent();
                    cloud.addStudent(s);
                } catch (EmptyBagException e) {}

            }
    }


    /**
     * Construct and initialize a 2 player game
     *
     * @param playerNames The list of the player names
     */
    protected Game2P(List<String> playerNames) throws InvalidNewGameException {
        int nOfIsland = 12;

        int nOfClouds = 2;
        int nOfTowersPerColor = 8;
        int nOfStudentsInEntry = 7;
        int totalCoins = 20;
        int startingCoins = 3;

        // Construct the islands
        islands = new ArrayList<>();
        for (int i = 0; i < nOfIsland; i++) {
            islands.add(new Island());
        }
        for (Island isl : islands) {
            isl.setNextIsland(getNextIsland(isl));
            isl.setPrevIsland(getPrevIsland(isl));
        }

        // Constructs mothernature
        motherNature = new MotherNature(islands.get(0));

        // Add the first students to the islands
        bag = Bag.getNewStartingBag();
        initIslandsWithStudents(bag);

        // Fill the bag with all the students
        Bag.getRefilledGameBag(bag);


        // Creates the clouds and fill them
        clouds = new ArrayList<>();
        for (int i = 0; i < nOfClouds; i++) {
            clouds.add(new Cloud());
        }
        refillClouds();

        // Add the professors to the gameBoard
        boardProfessors = new ArrayList<>();
        for (PawnColor color : PawnColor.values()) {
            boardProfessors.add(new Professor(color));
        }

        // Creates the players
        players = new ArrayList<>();
        for (String name : playerNames) {
            players.add(new Player(name, new School(), startingCoins));
        }


        // Create the players with their school
        for (int j = 0; j < players.size(); j++) {
            for (int i = 0; i < nOfStudentsInEntry; i++) {
                try {
                    players.get(j).getSchool().addEntrance(bag.pickStudent());
                } catch (EmptyBagException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < nOfTowersPerColor; i++) {
                players.get(j).getSchool().addTower(new Tower(j == 0 ? TowerColor.WHITE : TowerColor.BLACK, players.get(j)));
            }
        }

        // Set board coins
        boardCoins = totalCoins - startingCoins * players.size();

        // Set expert mode
        expert = false;
    }
}
