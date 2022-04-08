package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.entity.*;
import it.polimi.ingsw.model.enumeration.PawnColor;
import org.junit.jupiter.api.Test;

public class CloudTest {

    private final Cloud myCloud1 = new Cloud();

    @Test
    void addStudent() {

        myCloud1.addStudent(new Student(PawnColor.RED));
        myCloud1.addStudent(new Student(PawnColor.BLUE));
        myCloud1.addStudent(new Student(PawnColor.YELLOW));

        assertEquals(3, myCloud1.getStudents().size());

    }

    @Test
    void pickAllStudents() {

        myCloud1.addStudent(new Student(PawnColor.RED));
        myCloud1.addStudent(new Student(PawnColor.BLUE));
        myCloud1.addStudent(new Student(PawnColor.YELLOW));

        assertEquals(3, myCloud1.getStudents().size());

    }

}
