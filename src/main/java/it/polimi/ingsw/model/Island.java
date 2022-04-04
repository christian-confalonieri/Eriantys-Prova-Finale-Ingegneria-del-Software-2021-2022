package it.polimi.ingsw.model;

import it.polimi.ingsw.model.powers.EffectHandler;
import java.util.ArrayList;
import java.util.List;

//class EntityBase <T> {
//    public T key { get; set; }
//
//    public bool Equals(EntityBase<T> other) { }
//}
//
//class Pawn : EntityBase<int> {
//
//
//}
//
//class Island : EntityBase<UUID> {
//
//
//}

/**
 * The island implements the PawnHandler interface, contains the list of students, towers
 * and a boolean variable noEntry dedicated to the expert mode.
 * It also contains references to the previous and next island used by the Game() class.
 * It implements methods related to moving towers and students and calculating the influence of a player.
 */
public class Island {

    private final List<Student> students;
    private final List<Tower> towers;
    private boolean noEntry;
    private Island prevIsland;
    private Island nextIsland;

    public Island() {
        prevIsland = null;
        nextIsland = null;
        students = new ArrayList<>();
        towers = new ArrayList<>();
        noEntry = false;
    }

    public Island(Island prevIsland, Island nextIsland) {
        this.prevIsland = prevIsland;
        this.nextIsland = nextIsland;
        students = new ArrayList<>();
        towers = new ArrayList<>();
        noEntry = false;
    }

    /**
     * Adds a student on the island.
     *
     * @param student the student to be added
     */
    public void addStudent(Student student) {
        students.add(student);
    }

    /**
     * Removes a student on the island.
     *
     * @param student the student to be removed
     */
    public void removeStudent(Student student) { students.remove(student); }

    /**
     * Returns the size of the island.
     * The size of an island is defined as how many initial islands it consists of.
     *
     * @return the size of the island
     */
    public int getIslandSize() {

        int numberOfTowers = towers.size();

        if(numberOfTowers == 0) {
            return 1;
        }
        else {
            return numberOfTowers;
        }

    }

    /**
     * If towers are present on the island, it returns their color.
     * If there are no towers on the island it returns null.
     *
     * @return the color of the towers
     */
    public TowerColor getTowerColor() {

        if(!towers.isEmpty())  {
            return towers.get(0).getColor();
        }
        else {
            return null;
        }

    }

    /**
     * Returns the getInfluencePoints(List<Professor> professors) method which returns the number
     * of students of the color on which the player has the most influence.
     *
     * @param player the player on which to calculate the influence points
     * @return the influence points
     */
    public int getInfluencePoints(Player player, EffectHandler effectHandler) {

        List<Professor> professors = player.getSchool().getProfessorTable();
        int points = getInfluencePoints(professors, effectHandler);

        if (!towers.isEmpty() && towers.get(0).getOwner() == player && !effectHandler.isSkipTowers()) {
            points += towers.size();
        }

        // It is assumed to zero the value of additional influence at the end of the effect
        if(player==effectHandler.getEffectPlayer()) {
            points += effectHandler.getAdditionalInfluence();
        }

        return points;

    }

    /**
     * method for the 4-player mode
     *
     * Returns the getInfluencePoints(List<Professor> professors) method which returns the number
     * of students of the color on which the team has the most influence.
     *
     * @param team the team on which to calculate the influence points
     * @return the influence points
     */
    public int getInfluencePoints(Team team, EffectHandler effectHandler) {

        List<Professor> professors = team.getTeamProfessors();
        int points = getInfluencePoints(professors, effectHandler);

        if (!towers.isEmpty() && team.getPlayers().contains(towers.get(0).getOwner()) && !effectHandler.isSkipTowers()) {
            points += towers.size();
        }

        // It is assumed to zero the value of additional influence at the end of the effect
        if(team.getPlayers().contains(effectHandler.getEffectPlayer())) {
            points += effectHandler.getAdditionalInfluence();
        }

        return points;
    }

    /**
     * Check the professors of a given team.
     * Count the number of students on the island related to the same color as the professors.
     * Returns the largest number.
     *
     * @param professors the professors through which to calculate the influence points
     * @return the influence points
     */
    private int getInfluencePoints(List<Professor> professors, EffectHandler effectHandler) {

        int max=0;

        for(Professor professor: professors) {
            int i=0;
            for(Student student: students) {
                // It is supposed to set the value of harvesterColor to null at the end of the effect
                if(student.getColor() == professor.getColor() && student.getColor()!=effectHandler.getHarvesterColor()) {
                    i++;
                }
            }
            max+=i;
        }

        return max;

    }

    /**
     * Returns the player with the most influence on the island.
     * If there are towers on the island I compare the previous influence points with the new ones
     * and return the dominant player, in case of a tie I keep the current one.
     * If there are no towers on the island, I return the player with the most influence points.
     * If no player has influence on the island return null.
     *
     * @return the player with the most influence on the island, null if no one owns the island
     */
    public Player getInfluencePlayer(List<Player> players, EffectHandler effectHandler) {

        Player currentInfluencePlayer = null;
        int currentInfluencePoints, max = 0;
        Player prevInfluencePlayer = null;
        int prevInfluencePoints = 0;

        if(!towers.isEmpty())  {
            prevInfluencePlayer = towers.get(0).getOwner();
            prevInfluencePoints = getInfluencePoints(prevInfluencePlayer, effectHandler);
        }

        for(Player player: players) {
            if(player!=prevInfluencePlayer) {
                currentInfluencePoints = getInfluencePoints(player,effectHandler);
                if(currentInfluencePoints > max) {
                    max = currentInfluencePoints;
                    currentInfluencePlayer = player;
                }
            }
        }

        if(max > prevInfluencePoints) {
            return currentInfluencePlayer;
        }
        else {
            if (max == 0 && prevInfluencePoints == 0) {
                return null;
            } else {
                return prevInfluencePlayer;
            }
        }

    }

    /**
     * method for the 4-player mode
     *
     * Returns the team with the most influence on the island.
     * If there are towers on the island I compare the previous influence points with the new ones
     * and return the dominant team, in case of a tie I keep the current one.
     * If there are no towers on the island, I return the team with the most influence points.
     *If no team has influence on the island return null
     *
     * @return the team with the most influence on the island
     */
    public Team getInfluenceTeam(List<Team> teams, EffectHandler effectHandler) {

        Team currentInfluenceTeam = null;
        int currentInfluencePoints, max = 0;
        Team prevInfluenceTeam = null;
        int prevInfluencePoints = 0;

        if(!towers.isEmpty())  {
            Player ownerTower = towers.get(0).getOwner();
            for(Team team: teams) {
                for (Player player: team.getPlayers()) {
                    if(player == ownerTower) {
                        prevInfluenceTeam = team;
                        prevInfluencePoints = getInfluencePoints(prevInfluenceTeam, effectHandler);
                    }
                }
            }
        }

        for(Team team: teams) {
            if(team!=prevInfluenceTeam) {
                currentInfluencePoints = getInfluencePoints(team, effectHandler);
                if (currentInfluencePoints > max) {
                    max = currentInfluencePoints;
                    currentInfluenceTeam = team;
                }
            }
        }

        if(max > prevInfluencePoints) {
            return currentInfluenceTeam;
        }
        else {
            if (max == 0 && prevInfluencePoints == 0) {
                return null;
            } else {
                return prevInfluenceTeam;
            }
        }

    }

    /**
     * Move towers from the island to their owner's school.
     */
    public void moveTowers() {
        for(Tower tower: towers) {
            School destination = towers.get(0).getOwner().getSchool();
            destination.addTower(tower);
        }
        towers.clear();
    }

    /**
     * Adds a tower on the island.
     *
     * @param tower the tower that needs to be added
     */
    public void addTower(Tower tower) {
        towers.add(tower);
    }

    /**
     * Check if the current island and the next island can be merged.
     * Check if the two adjacent islands have towers of the same color.
     *
     * @return a boolean that tells if the two islands can be merged
     */
    public boolean checkUnifyNext() {
        if(!towers.isEmpty() && !nextIsland.towers.isEmpty()) {
                return towers.get(0).getColor() == nextIsland.towers.get(0).getColor();
        }
        else {
            return false;
        }
    }

    /**
     * Check if the current island and the previous island can be merged.
     * Check if the two adjacent islands have towers of the same color.
     *
     * @return a boolean that tells if the two islands can be merged
     */
    public boolean checkUnifyPrev() {
        if(!towers.isEmpty() && !prevIsland.towers.isEmpty()) {
            return towers.get(0).getColor() == prevIsland.towers.get(0).getColor();
        }
        else {
            return false;
        }
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public Island getPrevIsland() {
        return prevIsland;
    }

    public Island getNextIsland() {
        return nextIsland;
    }

    public void setPrevIsland(Island prevIsland) {
        this.prevIsland = prevIsland;
    }

    public void setNextIsland(Island nextIsland) {
        this.nextIsland = nextIsland;
    }

    public boolean isNoEntry() {
        return noEntry;
    }

    public void setNoEntry(boolean noEntry) {
        this.noEntry = noEntry;
    }
}
