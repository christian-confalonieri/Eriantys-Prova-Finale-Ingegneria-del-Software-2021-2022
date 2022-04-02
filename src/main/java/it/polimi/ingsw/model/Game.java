package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.exceptions.NotContainedException;

import java.nio.file.ProviderNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The game class represent the board of the game and contains all the objects
 * that interact with it.
 */
public abstract class Game {

    protected List<Island> islands;
    protected List<Player> players;
    protected List<Cloud> clouds;
    protected List<Professor> boardProfessors;
    protected MotherNature motherNature;
    protected Bag bag;

    protected GameRules gameRules;

    protected List<PowerCard> powerCards;
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
    public void professorRelocate() throws NotContainedException {
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
                if(p.getSchool().getStudentsNumber(color) > maxPlayerColor.getSchool().getStudentsNumber(color)) {
                    maxPlayerColor = p;
                    allZero = false;
                }
            }
            if (allZero) return;
            if (professorOnBoard) {
                maxPlayerColor.getSchool().addProfessor(profToMove);
                this.removeProfessor(profToMove);
            }
            else {
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
                } catch (EmptyBagException e) { e.printStackTrace(); }

            }
    }

    /**
     * Get the current game leaderboard.
     * Can be used in the middle of the game too.
     *
     * @return the game leaderboard
     */
    public List<Player> getLeaderBoard() {
        return players.stream()
                .sorted((p1, p2) -> {
                    if(p1.getSchool().getTowers().size() == p2.getSchool().getTowers().size())
                        return (p1.getSchool().getProfessorTable().size() > p2.getSchool().getProfessorTable().size()) ? 1 : -1;
                    return (p1.getSchool().getTowers().size() > p2.getSchool().getTowers().size()) ? 1 : -1;
                }).collect(Collectors.toList());
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

    /**
     * Return the first player of the turn after the preparation by comparing the last played card
     *
     * @return the player with the smallest number lastPlayedCard
     */
    public Player calculateFirstTurnPlayer() {
        return players.stream().reduce((p1, p2) -> p1.getLastPlayedCard().getNumber() < p2.getLastPlayedCard().getNumber() ? p1 : p2).get();
    }

    public Island getNextIsland(Island island) {
        int index = islands.indexOf(island);
        return islands.get((index + 1) % islands.size());
    }

    public Island getPrevIsland(Island island) {
        int index = islands.indexOf(island);
        return islands.get((index + islands.size() - 1) % islands.size());
    }

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
}
