package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Student extends Pawn{

    public Student(PawnColor color) {
        super(color);
    }

    /**
     * Generates n students equally distributed in color
     * Null if the number n is not a multiple of the number of colors
     *
     * @param n the number of students to generate
     * @return the list of the students generated, or null if n is not a multiple of the number of colors
     */
    static List<Student> generateNStudents(int n) {
        if(n % PawnColor.values().length != 0) return null;


        List<Student> studentList = new ArrayList<>();
        int nOfColor = n / PawnColor.values().length;
        for (int i = 0; i < nOfColor; i++) {
            for (PawnColor color : PawnColor.values()) {
                studentList.add(new Student(color));
            }
        }

        return studentList;

    }

}
