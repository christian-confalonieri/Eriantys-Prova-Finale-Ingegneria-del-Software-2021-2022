package it.polimi.ingsw.model;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.model.entity.*;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.enumeration.TowerColor;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.power.EffectHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class BagTest {

    private final List<Student> students = new ArrayList<>();
    private final Bag bag = new Bag(students);

    @Test
    void addAllStudentsTest () {
        students.add(new Student(PawnColor.RED));
        students.add(new Student(PawnColor.BLUE));
        bag.addAllStudents(students);
        List<Student> stud = bag.getStudents();
        assertTrue(stud.size() == 2);
    }

    @Test
    void pickStudentTest () throws EmptyBagException {
        int initialSize = 2;
        students.add(new Student(PawnColor.RED));
        students.add(new Student(PawnColor.BLUE));
        bag.addAllStudents(students);
        Student stud = bag.pickStudent();
        assertTrue(bag.getStudents().size() == initialSize - 1);
    }

    @Test
    void isEmptyTest () {
        assertTrue(bag.isEmpty() == true);
    }

}
