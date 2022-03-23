package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.EmptyBagException;

import java.util.List;
import java.util.Random;

public class Bag implements PawnHandler{

    private final Random randomGenerator;
    private final List<Student> students;

    public Bag(List<Student> students) {
        randomGenerator = new Random();
        this.students = students;
    }

    @Override
    public void addPawn(Pawn pawn) {

    }

    @Override
    public void movePawnTo(PawnHandler destination, Pawn pawn) {
        destination.addPawn(pawn);
    }

    public Student pickStudent() throws EmptyBagException{
        if(students.size() == 0) throw new EmptyBagException();
        return students.remove(randomGenerator.nextInt(students.size()));
    }

}
