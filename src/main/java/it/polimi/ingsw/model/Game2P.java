package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.exceptions.InvalidNewGameException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class Game2P extends Game {
    /**
     * Construct and initialize a 2 player game
     *
     * @param playersData A map containing the name of the player and the wizard of the player
     * @param gameRules an instance of the gameRules class containing the parameters of the game
     */
    protected Game2P(SortedMap<String, Wizard> playersData, GameRules gameRules) throws InvalidNewGameException {
        this.gameRules = gameRules;

        // Construct the islands
        islands = new ArrayList<>();
        for (int i = 0; i < gameRules.islandsRules.numberOfIslands; i++) {
            islands.add(new Island());
        }
        for (Island isl : islands) {
            isl.setNextIsland(getNextIsland(isl));
            isl.setPrevIsland(getPrevIsland(isl));
        }

        // Constructs mothernature
        motherNature = new MotherNature(islands.get(0));

        // Add the first students to the islands
        bag = Bag.getNewStartingBag(gameRules);
        initIslandsWithStudents(bag);

        // Fill the bag with all the students
        Bag.getRefilledGameBag(bag, gameRules);


        // Creates the clouds and fill them
        clouds = new ArrayList<>();
        for (int i = 0; i < gameRules.cloudsRules.numberOfClouds; i++) {
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
        for (String name : playersData.keySet()) {
            players.add(new Player(name, playersData.get(name), new School(), gameRules.coinRules.startingCoinsPerPlayer));
        }


        // Create the players with their school
        for (int j = 0; j < players.size(); j++) {
            for (int i = 0; i < gameRules.studentsRules.startingStudentsEntrance; i++) {
                try {
                    players.get(j).getSchool().addEntrance(bag.pickStudent());
                } catch (EmptyBagException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < gameRules.towersRules.numberOfTowers; i++) {
                players.get(j).getSchool().addTower(new Tower(j == 0 ? TowerColor.WHITE : TowerColor.BLACK, players.get(j)));
            }
        }

        // Set board coins
        boardCoins = gameRules.coinRules.startingBoardCoins;
    }
}
