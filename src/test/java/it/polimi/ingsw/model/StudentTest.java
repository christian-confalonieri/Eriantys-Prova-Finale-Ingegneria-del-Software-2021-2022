package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.entity.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class StudentTest {

    @Test
    void generateNStudentsPerColorTest () {
        ArrayList<Student> students = (ArrayList<Student>) Student.generateNStudentsPerColor(3);

        assertEquals(3*5, students.size());
    }
}
