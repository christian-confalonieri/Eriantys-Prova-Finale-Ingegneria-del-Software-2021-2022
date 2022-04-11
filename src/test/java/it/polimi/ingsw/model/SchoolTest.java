package it.polimi.ingsw.model;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.entity.*;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.enumeration.TowerColor;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.power.EffectHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class SchoolTest {

    private final School mySchool1 = new School();

    @Test
    void addEntrance () {
        Student student = new Student(PawnColor.RED);
        List<Student> entrance = new ArrayList<>();
        entrance.add(student);

        mySchool1.addEntrance(student);

        assertEquals(mySchool1.getEntrance(), entrance);
    }

    @Test
    void removeEntrance () {
        Student student = new Student(PawnColor.RED);
        List<Student> entrance = new ArrayList<>();
        entrance.add(student);

        mySchool1.addEntrance(student);

        entrance.remove(student);

        mySchool1.removeEntrance(student);

        assertEquals(mySchool1.getEntrance(), entrance);
    }

    @Test
    void addDiningRoom () {

    }
}
