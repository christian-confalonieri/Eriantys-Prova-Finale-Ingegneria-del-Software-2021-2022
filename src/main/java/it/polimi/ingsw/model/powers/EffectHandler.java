package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Student;

import java.util.List;

public class EffectHandler {

    private PawnColor chosenColor;
    private List<Student> chosenStudents;
    private Island chosenIsland;
    private int additionalMoves;
    private int additionalInfluence;
    private boolean skipTowers;
    private boolean effectActive;
    private Player effectPlayer;

}