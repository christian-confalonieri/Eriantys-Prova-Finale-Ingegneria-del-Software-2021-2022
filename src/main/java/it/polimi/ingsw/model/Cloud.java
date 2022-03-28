package it.polimi.ingsw.model;

import java.util.List;

public class Cloud implements PawnHandler{

    private List<Student> students;

    @Override
    public void addPawn(Pawn pawn) {
        // The pawn received as input is added to the list of students.
        this.students.add((Student) pawn);
    }

    @Override
    public void movePawnTo(PawnHandler destination, Pawn pawn) {
        students.remove((Student)pawn);
        destination.addPawn(pawn);
    }

    /**
     * Every student on the cloud is moved to another location.
     *
     * @param destination the destination of the students
     *
     */
    public void moveAllPawns(PawnHandler destination) {
        // Call the method movePawnTo for every student in the list
        for ( Student student : students ) {
            this.movePawnTo(destination, student);
            this.students.remove(student);
        }
    }

}
