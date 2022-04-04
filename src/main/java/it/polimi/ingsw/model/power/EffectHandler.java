package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.entity.Island;
import it.polimi.ingsw.model.entity.Professor;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.entity.Student;

import java.util.List;
import java.util.Map;

/**
 * @author Christian Confalonieri
 */
public class EffectHandler {

    private PawnColor harvesterColor;
    private PawnColor thiefColor;
    private List<Student> chosenStudents1;
    private List<Student> chosenStudents2;
    private Map<Player, List<Professor>> chosenProfessors;
    private Island chosenIsland;
    private int additionalMoves;
    private int additionalInfluence;
    private boolean skipTowers;
    private Player effectPlayer;

    /**
     * @author Christian Confalonieri
     */
    public EffectHandler() {

        harvesterColor = null;
        thiefColor = null;
        // I have added two lists of students, basically you will use the first one, the second one is needed in case of exchanges
        chosenStudents1 = null;
        chosenStudents2 = null;
        chosenProfessors = null;
        chosenIsland = null;
        additionalMoves = 0;
        additionalInfluence = 0;
        skipTowers = false;
        effectPlayer = null;

    }

    /**
     * @author Christian Confalonieri
     */
    public EffectHandler(PawnColor harvesterColor, PawnColor thiefColor, List<Student> chosenStudents1, List<Student> chosenStudents2, Map<Player,List<Professor>> chosenProfessors,
                         Island chosenIsland, int additionalMoves, int additionalInfluence, boolean skipTowers, Player effectPlayer) {
        this.harvesterColor = harvesterColor;
        this.thiefColor = thiefColor;
        this.chosenStudents1 = chosenStudents1;
        this.chosenStudents2 = chosenStudents2;
        this.chosenProfessors = chosenProfessors;
        this.chosenIsland = chosenIsland;
        this.additionalMoves = additionalMoves;
        this.additionalInfluence = additionalInfluence;
        this.skipTowers = skipTowers;
        this.effectPlayer = effectPlayer;
    }

    public PawnColor getHarvesterColor() {
        return harvesterColor;
    }

    public void setHarvesterColor(PawnColor harvesterColor) {
        this.harvesterColor = harvesterColor;
    }

    public PawnColor getThiefColor() {
        return thiefColor;
    }

    public void setThiefColor(PawnColor thiefColor) {
        this.thiefColor = thiefColor;
    }

    public List<Student> getChosenStudents1() {
        return chosenStudents1;
    }

    public void setChosenStudents1(List<Student> chosenStudents1) {
        this.chosenStudents1 = chosenStudents1;
    }

    public List<Student> getChosenStudents2() {
        return chosenStudents2;
    }

    public void setChosenStudents2(List<Student> chosenStudents2) {
        this.chosenStudents2 = chosenStudents2;
    }

    public Island getChosenIsland() {
        return chosenIsland;
    }

    public void setChosenIsland(Island chosenIsland) {
        this.chosenIsland = chosenIsland;
    }

    public int getAdditionalMoves() {
        return additionalMoves;
    }

    public void setAdditionalMoves(int additionalMoves) {
        this.additionalMoves = additionalMoves;
    }

    public int getAdditionalInfluence() {
        return additionalInfluence;
    }

    public void setAdditionalInfluence(int additionalInfluence) {
        this.additionalInfluence = additionalInfluence;
    }

    public boolean isSkipTowers() {
        return skipTowers;
    }

    public void setSkipTowers(boolean skipTowers) {
        this.skipTowers = skipTowers;
    }

    public Player getEffectPlayer() {
        return effectPlayer;
    }

    public void setEffectPlayer(Player effectPlayer) {
        this.effectPlayer = effectPlayer;
    }

    public Map<Player, List<Professor>> getChosenProfessors() {
        return chosenProfessors;
    }

    public void setChosenProfessors(Map<Player, List<Professor>> chosenProfessors) {
        this.chosenProfessors = chosenProfessors;
    }
}