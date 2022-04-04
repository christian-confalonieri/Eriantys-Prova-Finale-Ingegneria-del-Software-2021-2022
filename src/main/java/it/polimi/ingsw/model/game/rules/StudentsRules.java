package it.polimi.ingsw.model.game.rules;

import it.polimi.ingsw.exceptions.InvalidRulesException;

public class StudentsRules {
    public final int startingStudentsOnIsland;
    public final int startingStudentsEntrance;
    public final int turnStudents;

    StudentsRules(int startingStudentsOnIsland, int startingStudentsEntrance, int turnStudents) throws InvalidRulesException {
        if (startingStudentsOnIsland < 0) throw new InvalidRulesException();
        this.startingStudentsOnIsland = startingStudentsOnIsland;
        if (startingStudentsEntrance < 0) throw new InvalidRulesException();
        this.startingStudentsEntrance = startingStudentsEntrance;
        if (turnStudents < 0) throw new InvalidRulesException();
        this.turnStudents = turnStudents;
    }
}
