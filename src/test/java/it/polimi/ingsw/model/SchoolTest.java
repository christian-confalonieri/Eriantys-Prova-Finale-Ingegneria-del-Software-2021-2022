package it.polimi.ingsw.model;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.entity.*;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.enumeration.TowerColor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        Student student = new Student(PawnColor.RED);
        Map<PawnColor, List<Student>> diningRoom = new HashMap<>();
        mySchool1.addDiningRoom(student);
        for (PawnColor pawnColor: PawnColor.values()) {
            diningRoom.put(pawnColor, new ArrayList<>());
        }
        diningRoom.get(student.getColor()).add(student);

        assertEquals(diningRoom, mySchool1.getDiningRoom());
    }

    @Test
    void removeDiningRoom () {
        Student student = new Student(PawnColor.RED);
        Map<PawnColor, List<Student>> diningRoom = new HashMap<>();
        mySchool1.addDiningRoom(student);
        mySchool1.removeDiningRoom(student);
        for (PawnColor pawnColor: PawnColor.values()) {
            diningRoom.put(pawnColor, new ArrayList<>());
        }
        diningRoom.get(student.getColor()).add(student);
        diningRoom.get(student.getColor()).remove(student);

        assertEquals(diningRoom, mySchool1.getDiningRoom());
    }

    @Test
    void getStudentsDiningRoom () {
        Student student = new Student(PawnColor.RED);
        Map<PawnColor, List<Student>> diningRoom = new HashMap<>();
        mySchool1.addDiningRoom(student);
        for (PawnColor pawnColor: PawnColor.values()) {
            diningRoom.put(pawnColor, new ArrayList<>());
        }
        diningRoom.get(student.getColor()).add(student);

        assertEquals(diningRoom.get(PawnColor.RED), mySchool1.getStudentsDiningRoom(PawnColor.RED));
    }

    @Test
    void entranceToDiningRoomTest () {
        Student student = new Student(PawnColor.RED);
        mySchool1.addEntrance(student);
        mySchool1.entranceToDiningRoom(student);
        assertTrue(mySchool1.getDiningRoom().get(student.getColor()).size() == 1);
        assertTrue(mySchool1.getEntrance().size() == 0);
    }

    @Test
    void entranceToIslandTest () {
        Student student = new Student(PawnColor.RED);
        Island island = new Island();
        mySchool1.addEntrance(student);
        mySchool1.entranceToIsland(student, island);
        assertTrue(mySchool1.getEntrance().size() == 0);
        assertTrue(island.getStudents().size() == 1);
    }

    @Test
    void getStudentsNumberTest () {
        Student student = new Student(PawnColor.RED);
        mySchool1.addEntrance(student);
        mySchool1.entranceToDiningRoom(student);
        assertEquals(mySchool1.getStudentsNumber(PawnColor.RED), 1);
    }

    @Test
    void addProfessorTest () {
        Professor professor = new Professor(PawnColor.RED);
        mySchool1.addProfessor(professor);
        assertTrue(mySchool1.getProfessorTable().size() == 1);
    }

    @Test
    void removeProfessor () {
        Professor professor = new Professor(PawnColor.RED);
        mySchool1.addProfessor(professor);
        mySchool1.removeProfessor(professor);
        assertTrue(mySchool1.getProfessorTable().size() == 0);
    }

    @Test
    void hasProfessorTest () {
        Professor professor = new Professor(PawnColor.RED);
        mySchool1.addProfessor(professor);
        assertTrue(mySchool1.hasProfessor(PawnColor.RED) == true);
        assertFalse(mySchool1.hasProfessor(PawnColor.BLUE) == true);
    }

    @Test
    void moveTowerTest () {
        mySchool1.addTower(new Tower(TowerColor.WHITE, new Player()));
        mySchool1.moveTower(new Island());
        assertTrue(mySchool1.getTowers().size() == 0);
    }

    @Test
    void addTower () {
        mySchool1.addTower(new Tower(TowerColor.WHITE, new Player()));
        mySchool1.addTower(new Tower(TowerColor.WHITE, new Player()));
        assertTrue(mySchool1.getTowers().size() == 2);
    }
}
