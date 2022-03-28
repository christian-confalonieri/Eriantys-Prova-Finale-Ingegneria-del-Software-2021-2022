package it.polimi.ingsw.model;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Game implements PawnHandler {

    protected List<Island> islands;
    protected List<Player> players;
    protected List<Cloud> clouds;
    protected List<Professor> boardProfessors;
    protected MotherNature motherNature;
    protected Bag bag;
    protected List<PowerCard> powerCards;
    protected int boardCoins;
    protected int boardNoEntryCards;
    protected GameState gameState;

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
            if (professorOnBoard) { this.movePawnTo(maxPlayerColor.getSchool(), profToMove); }
            else { hasProfessor.getSchool().movePawnTo(maxPlayerColor.getSchool(), profToMove);}
        }
    }

    /**
     * Routine to refill all the board clouds with students picked from the bag
     */
    public abstract void refillClouds();


    /**
     * Check if the game is in an end situation and update the gameState if so
     *
     * @return true if the game has ended
     */
    public boolean checkEndGame() {
        boolean finished =  // One player has finished his towers
                players.stream().map(p -> p.getSchool().getTowers().size()).anyMatch(size -> size == 0) ||
                // There are only 3 islands
                islands.size() <= 3 ||
                // The cards or the students finished and all the players finished their turns
                (players.stream().map(p -> p.getHandCards().size()).anyMatch(size -> size == 0) ) //&& // TODO)
                // The students in the bag finished and all the players finished their turns
                || bag.isEmpty() //&& // TODO
                ;
        gameState.setEnded(finished);
        return finished;
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
     * @param pawn the professor to add to the boardProfessors
     */
    @Override
    public void addPawn(Pawn pawn) {
        boardProfessors.add((Professor) pawn);
    }


    /**
     * Move the professors on the board
     *
     * @param destination the destination school of the professor
     * @param pawn the professor to move
     */
    @Override
    public void movePawnTo(PawnHandler destination, Pawn pawn) {
        boardProfessors.remove((Professor) pawn);
        destination.addPawn(pawn);
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
}
