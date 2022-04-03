package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Student;

import java.util.ArrayList;
import java.util.List;

public class EffectHandler {

    private PawnColor harvesterColor;
    private PawnColor thiefColor;
    private List<Student> chosenStudents;
    private Island chosenIsland;
    private int additionalMoves;
    private int additionalInfluence;
    private boolean skipTowers;
    private boolean effectActive;
    private Player effectPlayer;

    public EffectHandler() {

        harvesterColor = null;
        thiefColor = null;
        chosenStudents = null;
        chosenStudents = null;
        additionalMoves = 0;
        additionalInfluence = 0;
        skipTowers = false;
        effectActive = false;
        effectPlayer = null;

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

    public List<Student> getChosenStudents() {
        return chosenStudents;
    }

    public void setChosenStudents(List<Student> chosenStudents) {
        this.chosenStudents = chosenStudents;
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

    public boolean isEffectActive() {
        return effectActive;
    }

    public void setEffectActive(boolean effectActive) {
        this.effectActive = effectActive;
    }

    public Player getEffectPlayer() {
        return effectPlayer;
    }

    public void setEffectPlayer(Player effectPlayer) {
        this.effectPlayer = effectPlayer;
    }

}