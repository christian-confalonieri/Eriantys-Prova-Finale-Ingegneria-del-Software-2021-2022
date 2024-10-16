package it.polimi.ingsw.model.entity;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.game.rules.GameRules;

import java.util.*;

public class Bag {
    private static final int nOfTotalStudents = PawnColor.values().length * 26;

    private final List<Student> students;

    /**
     * Create a new bag with all the students passed
     * The bag creates a new internal list by copy
     *
     * @param students the students to put in the bag
     */
    public Bag(List<Student> students) {
        this.students = new LinkedList<>(students);
        this.shuffle();
    }

    public List<Student> getStudents () {
        return this.students;
    }

    /**
     * Add all the students in the list to the bag
     *
     * @param students the list of students to add
     */
    public void addAllStudents(List<Student> students) {
        this.students.addAll(students);
    }

    /**
     * Pick a random student from the bag, removing it
     *
     * @return The picked student
     * @throws EmptyBagException if the bag is empty
     */
    public Student pickStudent() throws EmptyBagException {
        if(students.size() == 0) throw new EmptyBagException();
        return students.remove(0);
    }

    /**
     * @return true if the bag is empty
     */
    public boolean isEmpty() {
        return students.isEmpty();
    }

    private void shuffle() {
        Collections.shuffle(students);
    }

    /**
     * Get a new Bag filled only with the students that are used to create the new islands
     * @param gameRules the rules of the game
     * @return the Bag
     */
    public static Bag getNewStartingBag(GameRules gameRules) {
        double nOfStartingStudents = (double)(gameRules.islandsRules.numberOfIslands - 2) * (gameRules.studentsRules.startingStudentsOnIsland) / PawnColor.values().length;
        int roundedNOfStartingStudents = (int) Math.ceil(nOfStartingStudents);
        return new Bag(Student.generateNStudentsPerColor(roundedNOfStartingStudents));
    }

    /**
     * Given a used starting bag used to create the islands, refills it according to the game rules.
     * @param startingBag Initial bag used to set up the game
     * @param gameRules Game rules
     * @return The filled bag
     * @throws InvalidNewGameException Game Rules are not consistent
     */
    public static Bag getRefilledGameBag(Bag startingBag, GameRules gameRules) throws InvalidNewGameException {
        double nOfStartingStudents = (double)(gameRules.islandsRules.numberOfIslands - 2) * (gameRules.studentsRules.startingStudentsOnIsland) / PawnColor.values().length;
        int roundedNOfStartingStudents = (int) Math.ceil(nOfStartingStudents) * PawnColor.values().length;

        if(nOfTotalStudents < roundedNOfStartingStudents) throw new InvalidNewGameException("Too few total students in the bag");
        startingBag.addAllStudents(Student.generateNStudentsPerColor((nOfTotalStudents - roundedNOfStartingStudents) / PawnColor.values().length));
        startingBag.shuffle();
        return startingBag;
    }

}
