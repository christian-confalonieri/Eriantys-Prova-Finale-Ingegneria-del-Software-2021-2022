package it.polimi.ingsw.model.game;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.model.entity.*;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.enumeration.TowerColor;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.rules.GameRules;
import it.polimi.ingsw.model.power.EffectHandler;
import it.polimi.ingsw.model.power.Herbalist;
import it.polimi.ingsw.model.power.PowerCard;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.server.PlayerLobby;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The game class represent the board of the game and contains all the objects
 * that interact with it.
 */
public class Game {

    protected List<Island> islands;
    protected List<Player> players;
    protected List<Cloud> clouds;
    protected List<Professor> boardProfessors;
    protected MotherNature motherNature;
    protected Bag bag;

    protected GameRules gameRules;

    protected List<PowerCard> powerCards;
    protected EffectHandler effectHandler;
    protected int boardCoins;
    protected int boardNoEntryCards;

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
            island1.addStudent(s);
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
        for (PawnColor color : PawnColor.values()) {

            boolean professorOnBoard = boardProfessors.stream().map((Pawn::getColor)).anyMatch((col -> col == color));
            Player hasProfessor = professorOnBoard ? null :
                    players.stream()
                            .filter(player -> player.getSchool().hasProfessor(color))
                            .findAny()
                            .get();

            Player maxPlayerColor = professorOnBoard ? players.get(0) : hasProfessor; // The max starting player is the one with already the professor
            Professor profToMove = professorOnBoard ? this.getProfessor(color) : hasProfessor.getSchool().getProfessor(color);
            boolean allZero = professorOnBoard;



            for (Player p : players)
            {
                if (p.getSchool().getStudentsNumber(color) > 0) allZero = false;
                if (p.getSchool().getStudentsNumber(color) > maxPlayerColor.getSchool().getStudentsNumber(color)) {
                    maxPlayerColor = p;
                    allZero = false;
                }
            }

            if (professorOnBoard && !allZero) {
                maxPlayerColor.getSchool().addProfessor(profToMove);
                this.removeProfessor(profToMove);
            }
            else if(!allZero) {
                maxPlayerColor.getSchool().addProfessor(profToMove);
                hasProfessor.getSchool().removeProfessor(profToMove);
            }
        }
    }

    /**
     * Routine to refill all the board clouds with students picked from the bag
     */
    public void refillClouds() {
        for (Cloud cloud : clouds)
            for (int i = 0; i < gameRules.studentsRules.turnStudents; i++) {
                try {
                    Student s = bag.pickStudent();
                    cloud.addStudent(s);
                } catch (EmptyBagException e) { }
            }
    }

    /**
     * Get the current game leaderboard.
     * Can be used in the middle of the game too.
     *
     * @return the game leaderboard
     */
    public List<Player> getLeaderBoard() {
        List<Player> leaderBoard = new ArrayList<>(players);
        leaderBoard.sort(
                (p1, p2) -> {
                    if(p1.getSchool().getTowers().size() == p2.getSchool().getTowers().size()) {
                        return (p1.getSchool().getProfessorTable().size() > p2.getSchool().getProfessorTable().size()) ? -1 : 1;
                    }
                    return (p1.getSchool().getTowers().size() < p2.getSchool().getTowers().size()) ? -1 : 1;
                }
        );
        return leaderBoard;
    }


    /**
     * @param professor the professor to add to the boardProfessors
     */
    public void addProfessor(Professor professor) {
        boardProfessors.add(professor);
    }

    /**
     * Removes a professor from the professor's borard
     * @param professor The professor to be removed
     */
    public void removeProfessor(Professor professor) {
        this.boardProfessors.remove(professor);
    }

    /**
     * Returns true if on the board there is a professor of the given color
     * @param color The color for the professor to search
     * @return If the professor is on the board
     */
    public boolean hasProfessor(PawnColor color) {
        return boardProfessors.stream().map(Pawn::getColor).anyMatch(profColor -> profColor.equals(color));
    }

    /**
     * Return the professor object given its color
     * @param color The color for the professor to get
     * @return The professor
     * @throws java.util.NoSuchElementException if the professor is not on the board
     */
    public Professor getProfessor(PawnColor color) {
        return boardProfessors.stream().filter(professor -> professor.getColor().equals(color)).findAny().get();
    }

    public boolean isExpert() {
        return gameRules.expertMode;
    }


    public Island getNextIsland(Island island) {
        int index = islands.indexOf(island);
        return islands.get((index + 1) % islands.size());
    }


    public Island getPrevIsland(Island island) {
        int index = islands.indexOf(island);
        return islands.get((index + islands.size() - 1) % islands.size());
    }

    /**
     * Initialize all the games islands with the right number of students on them
     *
     * @param startingBag A starting bag to pick the students from
     */
    protected void initIslandsWithStudents(Bag startingBag) {
        // Fill all the clouds except 0 and middle one
        for (int i = 1; i < islands.size(); i++) {
            if(i != islands.size() / 2) {
                try {
                    for (int j = 0; j < gameRules.studentsRules.startingStudentsOnIsland; j++) {
                        islands.get(i).addStudent(startingBag.pickStudent());
                    }
                } catch (EmptyBagException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Given an island, calculates and move the towers if a player has conquered that island
     * and give the towers back to the previous owner
     *
     * @param island the island to conquered
     */
    public void conquerIsland(Island island) {
        Player previousOwner;
        Player influencePlayer = island.getInfluencePlayer(players, effectHandler);

        if (island.isNoEntry()) {
            island.setNoEntry(false);
            Herbalist herbalist = (Herbalist) powerCards.stream().filter(p -> p.getType().equals(PowerType.HERBALIST)).findAny().get();
            herbalist.setNoEntryCards(herbalist.getNoEntryCards() + 1);
            return;
        }

        if(!island.getTowers().isEmpty()) {
            previousOwner = island.getTowers().get(0).getOwner();
            if(previousOwner != influencePlayer) {
                int sizeIsland = island.getIslandSize();
                island.moveTowers();
                for (int i = 0; i < sizeIsland; i++) {
                    influencePlayer.getSchool().moveTower(island);
                }
            }
        }
        else {
            if(influencePlayer != null) {
                influencePlayer.getSchool().moveTower(island);
            }
        }
    }


    /**
     * Construct a game without the players inside
     *
     * @param gameRules an instance of the gameRules class containing the parameters of the game
     */
    protected Game(GameRules gameRules) throws InvalidNewGameException {
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

        // Set board coins
        boardCoins = gameRules.coinRules.startingBoardCoins;

        // Set noEntryCards
        boardNoEntryCards = 4;

        // Pick 3 PowerCards to use
        // Powercards are created by GameHandler constructor

        // Construct effectHandler
        effectHandler = new EffectHandler();
    }

    /**
     * Construct and initialize a player game with no team play
     *
     * @param playersData The list of PlayerLobby objects, containing the name of the player and the wizard of the player
     * @param gameRules the class containing all the parameters for game creation
     */
    protected Game(List<PlayerLobby> playersData, GameRules gameRules) throws InvalidNewGameException {
        this(gameRules);

        if(playersData.size() != gameRules.cloudsRules.numberOfClouds)
            throw new InvalidNewGameException("Bad rules: number of players doesn't match number of clouds");

        // Creates the players
        players = new ArrayList<>();
        for (PlayerLobby pData : playersData) {
            players.add(new Player(pData.getPlayerId(), pData.getWizard(), new School(), gameRules.coinRules.startingCoinsPerPlayer));
        }

        // Create the players with their school
        if(players.size() > TowerColor.values().length) throw new InvalidNewGameException("Not enough tower colors");
        Iterator<TowerColor> playerTowerColorIterator = Arrays.stream(TowerColor.values()).iterator();
        for (Player player : players) {
            for (int i = 0; i < gameRules.studentsRules.startingStudentsEntrance; i++) {
                try {
                    player.getSchool().addEntrance(bag.pickStudent());
                } catch (EmptyBagException e) {
                    e.printStackTrace();
                }
            }
            TowerColor towerColor = playerTowerColorIterator.next();
            for (int i = 0; i < gameRules.towersRules.numberOfTowers; i++) {
                player.getSchool().addTower(new Tower(towerColor, player));
            }
        }


    }

    public EffectHandler getEffectHandler() {
        return effectHandler;
    }

    public Bag getBag() {
        return bag;
    }

    public List<Island> getIslands() {
        return islands;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Cloud> getClouds() {
        return clouds;
    }

    public MotherNature getMotherNature() {
        return motherNature;
    }

    public void setPowerCards(List<PowerCard> powerCards) {
        this.powerCards = powerCards;
    }

    public GameRules getGameRules() {
        return gameRules;
    }

    public void setEffectHandler(EffectHandler effectHandler) {
        this.effectHandler = effectHandler;
    }

    public PowerCard getPowerCard(PowerType type) {
        for(PowerCard powerCard : powerCards) {
            if(powerCard.getType() == type) {
                return powerCard;
            }
        }
        return null;
    }

    public List<PowerCard> getPowerCards() {
        return powerCards;
    }
}
