package it.polimi.ingsw.model.entity;

import it.polimi.ingsw.model.enumeration.PawnColor;

import java.util.ArrayList;
import java.util.List;

public class Student extends Pawn{

    public Student(PawnColor color) {
        super(color);
    }

    /**
     * Generates n * numberOfColors students equally distributed in color
     *
     * @param n the number of students to generate
     * @return the list of the students generated
     */
    public static List<Student> generateNStudentsPerColor(int n) {

        List<Student> studentList = new ArrayList<>();
        for (PawnColor color : PawnColor.values()) {
            for (int i = 0; i < n; i++) {
                studentList.add(new Student(color));
            }
        }

        return studentList;
    }

}
