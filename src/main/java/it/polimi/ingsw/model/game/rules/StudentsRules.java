package it.polimi.ingsw.model.game.rules;

import it.polimi.ingsw.exceptions.InvalidRulesException;

public class StudentsRules {
    public final int startingStudentsOnIsland;
    public final int startingStudentsEntrance;
    public final int turnStudents;

    protected void validate() throws InvalidRulesException {
        if (startingStudentsOnIsland < 0) throw new InvalidRulesException();
        if (startingStudentsEntrance < 0) throw new InvalidRulesException();
        if (turnStudents < 0) throw new InvalidRulesException();
    }

    protected StudentsRules(int startingStudentsOnIsland, int startingStudentsEntrance, int turnStudents) throws InvalidRulesException {
        this.startingStudentsOnIsland = startingStudentsOnIsland;
        this.startingStudentsEntrance = startingStudentsEntrance;
        this.turnStudents = turnStudents;
    }
}
