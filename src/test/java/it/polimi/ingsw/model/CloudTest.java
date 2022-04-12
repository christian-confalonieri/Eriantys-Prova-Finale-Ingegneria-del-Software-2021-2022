package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.entity.*;
import it.polimi.ingsw.model.enumeration.PawnColor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christian Confalonieri
 */
public class CloudTest {

    private final Cloud myCloud1 = new Cloud();

    /**
     * @author Christian Confalonieri
     */
    @Test
    void addStudent() {

        Student student1 = new Student(PawnColor.RED);
        Student student2 = new Student(PawnColor.BLUE);
        Student student3 = new Student(PawnColor.YELLOW);
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);

        myCloud1.addStudent(student1);
        myCloud1.addStudent(student2);
        myCloud1.addStudent(student3);

        assertEquals(3, myCloud1.getStudents().size());
        assertTrue(myCloud1.getStudents().containsAll(students));

    }

    /**
     * @author Christian Confalonieri
     */
    @Test
    void pickAllStudents() {

        Student student1 = new Student(PawnColor.RED);
        Student student2 = new Student(PawnColor.BLUE);
        Student student3 = new Student(PawnColor.YELLOW);
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);

        myCloud1.addStudent(student1);
        myCloud1.addStudent(student2);
        myCloud1.addStudent(student3);

        assertEquals(students,myCloud1.pickAllStudents());
        assertTrue(myCloud1.getStudents().isEmpty());

    }

}
