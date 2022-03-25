package it.polimi.ingsw.model;

import java.util.List;

public abstract class Game implements PawnHandler {

    private List<Island> islands;
    private List<Player> players;
    protected List<Cloud> clouds;
    private List<Professor> boardProfessors;
    private MotherNature motherNature;
    protected Bag bag;
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
     * @return The leaderboard if the game has ended. Otherwise return null.
     */
    public List<Player> checkEndGame() {

    }


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
        boardProfessors.remove(pawn);
        destination.addPawn(pawn);
    }

    public boolean hasProfessor(PawnColor color) {
        return boardProfessors.stream().map(Pawn::getColor).anyMatch(profColor -> profColor.equals(color));
    }

    public Professor getProfessor(PawnColor color) {
        return boardProfessors.stream().filter(professor -> professor.getColor().equals(color)).findAny().get();
    }
}
