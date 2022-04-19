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

/**
 * @author Christian Confalonieri
 */
class IslandTest {

    private final Island myIsland1 = new Island();
    private final Island myIsland2 = new Island();
    private final Island myIsland3 = new Island();

    private final School mySchool1 = new School();
    private final School mySchool2 = new School();
    private final School mySchool3 = new School();
    private final School mySchool4 = new School();

    private final Player myPlayer1 = new Player("Mario", Wizard.GREEN, mySchool1, TowerColor.WHITE,0);
    private final Player myPlayer2 = new Player("Luigi", Wizard.PURPLE, mySchool2, TowerColor.BLACK,0);
    private final Player myPlayer3 = new Player("Wario", Wizard.YELLOW, mySchool3,null, 0);
    private final Player myPlayer4 = new Player("Waluigi", Wizard.BLUE, mySchool4, null, 0);

    private final EffectHandler effectHandler = new EffectHandler();

    /**
     * @author Christian Confalonieri
     */
    @Test
    void addStudent() {

        myIsland1.addStudent(new Student(PawnColor.RED));
        myIsland1.addStudent(new Student(PawnColor.BLUE));
        myIsland1.addStudent(new Student(PawnColor.YELLOW));

        assertEquals(3, myIsland1.getStudents().size());

    }

    /**
     * @author Christian Confalonieri
     */
    @Test
    void removeStudent() {

        Student myStudent1 = new Student(PawnColor.RED);
        Student myStudent2= new Student(PawnColor.BLUE);
        Student myStudent3 = new Student(PawnColor.YELLOW);

        myIsland1.addStudent(myStudent1);
        myIsland1.addStudent(myStudent2);
        myIsland1.addStudent(myStudent3);

        assertEquals(3, myIsland1.getStudents().size());

        myIsland1.removeStudent(myStudent1);
        myIsland1.removeStudent(myStudent2);
        myIsland1.removeStudent(myStudent3);

        assertEquals(0, myIsland1.getStudents().size());
    }

    /**
     * @author Christian Confalonieri
     */
    @Test
    void getIslandSize() {

        // base case (1 island)
        assertEquals(1, myIsland1.getIslandSize());

        // one tower (1 island)
        myIsland1.addTower(new Tower(TowerColor.WHITE, myPlayer1));

        assertEquals(1, myIsland1.getIslandSize());

        // more towers (more islands)
        myIsland1.addTower(new Tower(TowerColor.WHITE, myPlayer1));
        myIsland1.addTower(new Tower(TowerColor.WHITE, myPlayer1));

        assertEquals(3, myIsland1.getIslandSize());




    }

    /**
     * @author Christian Confalonieri
     */
    @Test
    void getTowerColor() {

        Tower myTower = new Tower(TowerColor.WHITE, myPlayer1);
        myIsland1.addTower(myTower);
        assertEquals("WHITE",myIsland1.getTowerColor().toString());

    }

    /**
     * @author Christian Confalonieri
     */
    @Test
    void getInfluencePointsPlayer() {

        Professor professor1 = new Professor(PawnColor.RED);
        Professor professor2 = new Professor(PawnColor.GREEN);

        // no influence points
        assertEquals(0,myIsland1.getInfluencePoints(myPlayer1, effectHandler));

        // standard case
        mySchool1.addProfessor(professor1);
        mySchool1.addProfessor(professor2);

        populateIsland(myIsland1,4,6,6,5,0);

        assertEquals(10,myIsland1.getInfluencePoints(myPlayer1, effectHandler));

        // 1 tower on the island
        myIsland1.addTower(new Tower(TowerColor.WHITE,myPlayer1));

        assertEquals(11,myIsland1.getInfluencePoints(myPlayer1, effectHandler));

        // 3 towers on the island
        myIsland1.addTower(new Tower(TowerColor.WHITE,myPlayer1));
        myIsland1.addTower(new Tower(TowerColor.WHITE,myPlayer1));

        assertEquals(13,myIsland1.getInfluencePoints(myPlayer1, effectHandler));

        // 1 tower of another player on the island

        populateIsland(myIsland2,4,6,6,5,0);
        myIsland2.addTower(new Tower(TowerColor.BLACK,myPlayer2));

        assertEquals(10,myIsland2.getInfluencePoints(myPlayer1, effectHandler));

    }

    /**
     * @author Christian Confalonieri
     */
    @Test
    void GetInfluencePointsTeam() {

        mySchool1.addTower(new Tower(TowerColor.WHITE,myPlayer1));
        mySchool2.addTower(new Tower(TowerColor.BLACK,myPlayer2));

        List<Player> myPlayers1 = new ArrayList<>();
        myPlayers1.add(myPlayer1);
        myPlayers1.add(myPlayer3);

        Team myTeam1 = new Team(myPlayers1);

        Professor professor1 = new Professor(PawnColor.RED);
        Professor professor2 = new Professor(PawnColor.GREEN);
        Professor professor3 = new Professor(PawnColor.BLUE);
        Professor professor4 = new Professor(PawnColor.YELLOW);

        // no influence points
        assertEquals(0,myIsland1.getInfluencePoints(myTeam1, effectHandler));

        // standard case
        mySchool1.addProfessor(professor1);
        mySchool1.addProfessor(professor2);
        mySchool2.addProfessor(professor3);
        mySchool3.addProfessor(professor4);

        populateIsland(myIsland1,4,6,6,5,0);

        assertEquals(16,myIsland1.getInfluencePoints(myTeam1, effectHandler));

        // 1 tower on the island
        myIsland1.addTower(new Tower(TowerColor.WHITE,myPlayer1));

        assertEquals(17,myIsland1.getInfluencePoints(myTeam1, effectHandler));

        // 3 towers on the island
        myIsland1.addTower(new Tower(TowerColor.WHITE,myPlayer1));
        myIsland1.addTower(new Tower(TowerColor.WHITE,myPlayer1));

        assertEquals(19,myIsland1.getInfluencePoints(myTeam1, effectHandler));

        // 1 tower of another player on the island

        populateIsland(myIsland2,4,6,6,5,0);
        myIsland2.addTower(new Tower(TowerColor.BLACK,myPlayer2));

        assertEquals(16,myIsland2.getInfluencePoints(myTeam1, effectHandler));

    }

    /**
     * @author Christian Confalonieri
     */
    @Test
    void getInfluencePlayer() {

        Professor professor1 = new Professor(PawnColor.RED);
        Professor professor2 = new Professor(PawnColor.GREEN);
        Professor professor3 = new Professor(PawnColor.PINK);

        mySchool1.addTower(new Tower(TowerColor.WHITE,myPlayer1));

        List<Player> myPlayers = new ArrayList<>();
        myPlayers.add(myPlayer1);
        myPlayers.add(myPlayer2);

        mySchool1.addProfessor(professor1);
        mySchool1.addProfessor(professor2);
        mySchool2.addProfessor(professor3);

        // standard case

        populateIsland(myIsland1,4,6,6,5,0);

        assertEquals(myPlayer1, myIsland1.getInfluencePlayer(myPlayers, effectHandler));

        populateIsland(myIsland2,4,6,6,5,11);

        assertEquals(myPlayer2, myIsland2.getInfluencePlayer(myPlayers, effectHandler));

        // no influence

        mySchool1.removeProfessor(professor1);
        mySchool1.removeProfessor(professor2);
        mySchool2.removeProfessor(professor3);

        assertNull(myIsland1.getInfluencePlayer(myPlayers, effectHandler));

        // island with towers

        myIsland1.addTower(new Tower(TowerColor.WHITE,myPlayer1));
        myIsland1.addTower(new Tower(TowerColor.WHITE,myPlayer1));
        myIsland1.addTower(new Tower(TowerColor.WHITE,myPlayer1));

        mySchool1.addProfessor(professor1);
        mySchool2.addProfessor(professor2);

        assertEquals(myPlayer1, myIsland1.getInfluencePlayer(myPlayers, effectHandler));

        // island with towers but skipTowers = TRUE
        effectHandler.setSkipTowers(true);

        assertEquals(myPlayer2, myIsland1.getInfluencePlayer(myPlayers, effectHandler));

        effectHandler.setSkipTowers(false);

        // island with additionalInfluence = 2
        effectHandler.setAdditionalInfluence(2);
        effectHandler.setEffectPlayer(myPlayer2);

        assertEquals(myPlayer2, myIsland1.getInfluencePlayer(myPlayers, effectHandler));

        effectHandler.setAdditionalInfluence(0);
        effectHandler.setEffectPlayer(null);

    }

    /**
     * @author Christian Confalonieri
     */
    @Test
    void getInfluenceTeam() {

        Professor professor1 = new Professor(PawnColor.RED);
        Professor professor2 = new Professor(PawnColor.GREEN);
        Professor professor3 = new Professor(PawnColor.PINK);

        mySchool1.addTower(new Tower(TowerColor.WHITE,myPlayer1));
        mySchool2.addTower(new Tower(TowerColor.BLACK,myPlayer2));

        List<Player> myPlayers1 = new ArrayList<>();
        myPlayers1.add(myPlayer1);
        myPlayers1.add(myPlayer3);

        Team myTeam1 = new Team(myPlayers1);

        List<Player> myPlayers2 = new ArrayList<>();
        myPlayers2.add(myPlayer2);
        myPlayers2.add(myPlayer4);

        Team myTeam2 = new Team(myPlayers2);

        List<Team> myTeams = new ArrayList<>();
        myTeams.add(myTeam1);
        myTeams.add(myTeam2);

        mySchool1.addProfessor(professor1);
        mySchool3.addProfessor(professor2);
        mySchool2.addProfessor(professor3);


        // standard case
        populateIsland(myIsland1,4,6,6,5,0);

        assertEquals(myTeam1,myIsland1.getInfluenceTeam(myTeams, effectHandler));

        assertEquals(myTeam1, myIsland1.getInfluenceTeam(myTeams, effectHandler));

        populateIsland(myIsland2,4,6,6,5,11);

        assertEquals(myTeam2, myIsland2.getInfluenceTeam(myTeams, effectHandler));

        // no influence

        mySchool1.removeProfessor(professor1);
        mySchool3.removeProfessor(professor2);
        mySchool2.removeProfessor(professor3);

        assertNull(myIsland1.getInfluenceTeam(myTeams, effectHandler));

        // island with towers

        myIsland1.addTower(new Tower(TowerColor.WHITE,myPlayer1));
        myIsland1.addTower(new Tower(TowerColor.WHITE,myPlayer1));
        myIsland1.addTower(new Tower(TowerColor.WHITE,myPlayer1));

        mySchool1.addProfessor(professor1);
        mySchool2.addProfessor(professor2);

        assertEquals(myTeam1, myIsland1.getInfluenceTeam(myTeams, effectHandler));

        // island with towers but skipTowers = TRUE
        effectHandler.setSkipTowers(true);

        assertEquals(myTeam2, myIsland1.getInfluenceTeam(myTeams, effectHandler));

        effectHandler.setSkipTowers(false);

        // island with additionalInfluence = 2
        effectHandler.setAdditionalInfluence(2);
        effectHandler.setEffectPlayer(myPlayer2);

        assertEquals(myTeam2, myIsland1.getInfluenceTeam(myTeams, effectHandler));

        effectHandler.setAdditionalInfluence(0);
        effectHandler.setEffectPlayer(null);

    }

    /**
     * @author Christian Confalonieri
     */
    @Test
    void moveTowers() {

        Tower myTower1 = new Tower(TowerColor.WHITE, myPlayer1);
        Tower myTower2 = new Tower(TowerColor.WHITE, myPlayer1);
        Tower myTower3 = new Tower(TowerColor.WHITE, myPlayer1);

        myIsland1.addTower(myTower1);
        myIsland1.addTower(myTower2);
        myIsland1.addTower(myTower3);

        assertEquals(3,myIsland1.getTowers().size());

        myIsland1.moveTowers();

        assertEquals(0,myIsland1.getTowers().size());
        assertEquals(3,mySchool1.getTowers().size());

    }

    /**
     * @author Christian Confalonieri
     */
    @Test
    void addTower() {

        myIsland1.addTower(new Tower(TowerColor.WHITE, myPlayer1));
        myIsland1.addTower(new Tower(TowerColor.WHITE, myPlayer1));
        myIsland1.addTower(new Tower(TowerColor.WHITE, myPlayer1));

        assertEquals(3, myIsland1.getTowers().size());

    }

    /**
     * @author Christian Confalonieri
     */
    @Test
    void checkUnifyNext() {

        myIsland1.setNextIsland(myIsland2);
        myIsland2.setNextIsland(myIsland3);
        myIsland3.setNextIsland(myIsland1);

        myIsland1.addTower(new Tower(TowerColor.BLACK, myPlayer1));
        myIsland2.addTower(new Tower(TowerColor.BLACK, myPlayer1));


        assertTrue(myIsland1.checkUnifyNext());
        assertFalse(myIsland2.checkUnifyNext());

    }

    /**
     * @author Christian Confalonieri
     */
    @Test
    void checkUnifyPrev() {

        myIsland1.setPrevIsland(myIsland3);
        myIsland2.setPrevIsland(myIsland1);
        myIsland3.setPrevIsland(myIsland2);

        myIsland1.addTower(new Tower(TowerColor.BLACK, myPlayer1));
        myIsland2.addTower(new Tower(TowerColor.BLACK, myPlayer1));


        assertTrue(myIsland2.checkUnifyPrev());
        assertFalse(myIsland1.checkUnifyPrev());

    }

    /**
     * @author Christian Confalonieri
     */
    private void populateIsland(Island island, int red, int yellow, int green, int blue, int pink) {

        for(int i=0;i<red;i++) {
            island.addStudent(new Student(PawnColor.RED));
        }

        for(int i=0;i<yellow;i++) {
            island.addStudent(new Student(PawnColor.YELLOW));
        }

        for(int i=0;i<green;i++) {
            island.addStudent(new Student(PawnColor.GREEN));
        }

        for(int i=0;i<blue;i++) {
            island.addStudent(new Student(PawnColor.BLUE));
        }

        for(int i=0;i<pink;i++) {
            island.addStudent(new Student(PawnColor.PINK));
        }
    }

}