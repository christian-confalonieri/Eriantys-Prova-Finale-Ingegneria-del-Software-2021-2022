package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.EmptyBagException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bag{

    private final Random randomGenerator;
    private final List<Student> students;

    /**
     * Create a new bag with all the students passed
     * The bag creates a new internal list by copy
     *
     * @param students the students to put in the bag
     */
    public Bag(List<Student> students) {
        randomGenerator = new Random();
        this.students = new ArrayList<>(students);
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
    public Student pickStudent() throws EmptyBagException{
        if(students.size() == 0) throw new EmptyBagException();
        return students.remove(randomGenerator.nextInt(students.size()));
    }

    /**
     * @return true if the bag is empty
     */
    public boolean isEmpty() {
        return students.isEmpty();
    }

}
