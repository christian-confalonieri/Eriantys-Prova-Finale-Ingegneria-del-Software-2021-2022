package it.polimi.ingsw.model;

import java.util.List;

public class Island implements PawnHandler {

    private List<Student> students;
    private List<Tower> towers;
    private boolean noEntry;
    private Island prevIsland;
    private Island nextIsland;

    @Override
    public void addPawn(Pawn pawn) {

    }

    @Override
    public void movePawnTo(PawnHandler destination, Pawn pawn) {

    }

    public int getIslandSize() {

    }

    public TowerColor getTowerColor() {

    }

    public int getInfluencePoints(Player player) {

    }

    public Player getInfluencePlayer(List<Player> players) {

    }

    public void moveTowers() {

    }

    public void addTower(Tower tower) {

    }
    
    public Island getPrevIsland() {
        return prevIsland;
    }

    public Island getNextIsland() {
        return nextIsland;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public void setPrevIsland(Island prevIsland) {
        this.prevIsland = prevIsland;
    }

    public void setNextIsland(Island nextIsland) {
        this.nextIsland = nextIsland;
    }

    public int getInfluenceTeam(Team team) {

    }

    public Team getInfluenceTeam(List<Team> team) {

    }
}
