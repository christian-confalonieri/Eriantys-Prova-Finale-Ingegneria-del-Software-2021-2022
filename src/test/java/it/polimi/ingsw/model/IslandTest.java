package it.polimi.ingsw.model;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.NotContainedException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

class IslandTest {

    Island myIsland1 = new Island();
    Island myIsland2 = new Island();
    Island myIsland3 = new Island();
    School mySchool1 = new School(null,new ArrayList<>());
    School mySchool2 = new School(null,new ArrayList<>());

    Student student1 = new Student(PawnColor.RED);
    Student student2 = new Student(PawnColor.BLUE);
    Student student3 = new Student(PawnColor.YELLOW);

    @Test
    void addStudent() {

        myIsland1.addStudent(student1);
        myIsland1.addStudent(student2);
        myIsland1.addStudent(student3);

        assertEquals(3, myIsland1.getStudents().size());

    }

    @Test
    void removeStudent() throws NotContainedException {

        myIsland1.addStudent(student1);
        myIsland1.addStudent(student2);
        myIsland1.addStudent(student3);

        assertEquals(3, myIsland1.getStudents().size());

        myIsland1.removeStudent(student1);
        myIsland1.removeStudent(student2);
        myIsland1.removeStudent(student3);

        assertEquals(0, myIsland1.getStudents().size());
    }

    @Test
    void getIslandSize() {

        assertEquals(1, myIsland1.getIslandSize());

        myIsland1.addTower(new Tower(TowerColor.WHITE, new Player()));

        assertEquals(1, myIsland1.getIslandSize());

        myIsland1.addTower(new Tower(TowerColor.WHITE, new Player()));
        myIsland1.addTower(new Tower(TowerColor.WHITE, new Player()));

        assertEquals(3, myIsland1.getIslandSize());

    }

    @Test
    void getTowerColor() {

        Tower myTower = new Tower(TowerColor.WHITE, new Player());
        myIsland1.addTower(myTower);
        assertEquals("WHITE",myIsland1.getTowerColor().toString());

    }

    @Test
    void getInfluencePointsPlayer() {

        Player myPlayer = new Player("Mario",mySchool1,0);
        mySchool1.addProfessor(new Professor(PawnColor.RED));
        mySchool1.addProfessor(new Professor(PawnColor.GREEN));
        int red=4, yellow=6, green= 6, blue=5, pink=0;

        for(int i=0;i<red;i++) {
            myIsland1.addStudent(new Student(PawnColor.RED));
        }

        for(int i=0;i<yellow;i++) {
            myIsland1.addStudent(new Student(PawnColor.YELLOW));
        }

        for(int i=0;i<green;i++) {
            myIsland1.addStudent(new Student(PawnColor.GREEN));
        }

        for(int i=0;i<blue;i++) {
            myIsland1.addStudent(new Student(PawnColor.BLUE));
        }

        for(int i=0;i<pink;i++) {
            myIsland1.addStudent(new Student(PawnColor.PINK));
        }

        assertEquals(10,myIsland1.getInfluencePoints(myPlayer));
    }

    @Test
    void GetInfluencePointsTeam() {

        Player myPlayer1 = new Player("Mario",mySchool1,0);
        Player myPlayer2 = new Player("Luigi",mySchool2,0);
        mySchool1.addTower(new Tower(TowerColor.WHITE,myPlayer1));



        List<Player> myPlayers = new ArrayList<>();
        myPlayers.add(myPlayer1);
        myPlayers.add(myPlayer2);

        Team myTeam = new Team(myPlayers);
        mySchool1.addProfessor(new Professor(PawnColor.RED));
        mySchool1.addProfessor(new Professor(PawnColor.GREEN));
        mySchool2.addProfessor(new Professor(PawnColor.PINK));
        int red=4, yellow=6, green= 6, blue=5, pink=0;

        for(int i=0;i<red;i++) {
            myIsland1.addStudent(new Student(PawnColor.RED));
        }

        for(int i=0;i<yellow;i++) {
            myIsland1.addStudent(new Student(PawnColor.YELLOW));
        }

        for(int i=0;i<green;i++) {
            myIsland1.addStudent(new Student(PawnColor.GREEN));
        }

        for(int i=0;i<blue;i++) {
            myIsland1.addStudent(new Student(PawnColor.BLUE));
        }

        for(int i=0;i<pink;i++) {
            myIsland1.addStudent(new Student(PawnColor.PINK));
        }

        assertEquals(10,myIsland1.getInfluencePoints(myTeam));
    }

    @Test
    void getInfluencePlayer() {
    }

    @Test
    void getInfluenceTeam() {
    }

    @Test
    void moveTowers() {
        
        Player myPlayer = new Player("Luigi", mySchool1,0);

        Tower myTower1 = new Tower(TowerColor.WHITE, myPlayer);
        Tower myTower2 = new Tower(TowerColor.WHITE, myPlayer);
        Tower myTower3 = new Tower(TowerColor.WHITE, myPlayer);

        myIsland1.addTower(myTower1);
        myIsland1.addTower(myTower2);
        myIsland1.addTower(myTower3);

        assertEquals(3,myIsland1.getTowers().size());

        myIsland1.moveTowers();

        assertEquals(0,myIsland1.getTowers().size());
        assertEquals(3,mySchool1.getTowers().size());

    }

    @Test
    void addTower() {

        myIsland1.addTower(new Tower(TowerColor.WHITE, new Player()));
        myIsland1.addTower(new Tower(TowerColor.WHITE, new Player()));
        myIsland1.addTower(new Tower(TowerColor.WHITE, new Player()));

        assertEquals(3, myIsland1.getTowers().size());

    }

    @Test
    void checkUnifyNext() {

        myIsland1.setNextIsland(myIsland2);
        myIsland2.setNextIsland(myIsland3);
        myIsland3.setNextIsland(myIsland1);

        myIsland1.addTower(new Tower(TowerColor.BLACK, new Player()));
        myIsland2.addTower(new Tower(TowerColor.BLACK, new Player()));


        assertTrue(myIsland1.checkUnifyNext());
        assertFalse(myIsland2.checkUnifyNext());

    }

    @Test
    void checkUnifyPrev() {

        myIsland1.setPrevIsland(myIsland3);
        myIsland2.setPrevIsland(myIsland1);
        myIsland3.setPrevIsland(myIsland2);

        myIsland1.addTower(new Tower(TowerColor.BLACK, new Player()));
        myIsland2.addTower(new Tower(TowerColor.BLACK, new Player()));


        assertTrue(myIsland2.checkUnifyPrev());
        assertFalse(myIsland1.checkUnifyPrev());

    }

}