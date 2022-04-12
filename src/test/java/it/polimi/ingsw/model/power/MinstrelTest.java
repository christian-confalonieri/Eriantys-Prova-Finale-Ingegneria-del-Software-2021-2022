package it.polimi.ingsw.model.power;

import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.exceptions.InvalidRulesException;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.entity.Professor;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.GameCreator;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.game.rules.GameRules;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Christian Confalonieri
 */
class MinstrelTest {

    /**
     * @author Christian Confalonieri
     */
    @Test
    void power() throws InvalidNewGameException, IOException, InvalidRulesException {
        GameHandler gameHandler = createGame();

        Minstrel minstrel = new Minstrel(gameHandler);
        List<PowerCard> powerCards = new ArrayList<>();
        powerCards.add(minstrel);
        gameHandler.getGame().setPowerCards(powerCards);

        assertEquals(PowerType.MINSTREL,minstrel.getType());
        assertEquals(1,minstrel.getCost());

        List<Player> players = gameHandler.getOrderedTurnPlayers();
        // 0: Luigi, 1: Mario
        Professor redProfessor = gameHandler.getGame().getProfessor(PawnColor.RED);;
        Professor greenProfessor = gameHandler.getGame().getProfessor(PawnColor.GREEN);;
        Professor yellowProfessor = gameHandler.getGame().getProfessor(PawnColor.YELLOW);;

        players.get(1).getSchool().addProfessor(redProfessor);
        gameHandler.getGame().removeProfessor(redProfessor);
        players.get(0).getSchool().addProfessor(greenProfessor);
        gameHandler.getGame().removeProfessor(greenProfessor);
        players.get(0).getSchool().addProfessor(yellowProfessor);
        gameHandler.getGame().removeProfessor(yellowProfessor);

        // add students in the Luigi's dining room
        for(int i=0; i<3; i++) {
            players.get(0).getSchool().addDiningRoom(new Student(PawnColor.RED));
        }
        for(int i=0; i<3; i++) {
            players.get(0).getSchool().addDiningRoom(new Student(PawnColor.GREEN));
        }
        for(int i=0; i<0; i++) {
            players.get(0).getSchool().addDiningRoom(new Student(PawnColor.BLUE));
        }
        for(int i=0; i<4; i++) {
            players.get(0).getSchool().addDiningRoom(new Student(PawnColor.YELLOW));
        }
        for(int i=0; i<0; i++) {
            players.get(0).getSchool().addDiningRoom(new Student(PawnColor.PINK));
        }

        // add students in the Mario's dining room
        for(int i=0; i<6; i++) {
            players.get(1).getSchool().addDiningRoom(new Student(PawnColor.RED));
        }
        for(int i=0; i<3; i++) {
            players.get(1).getSchool().addDiningRoom(new Student(PawnColor.GREEN));
        }
        for(int i=0; i<0; i++) {
            players.get(1).getSchool().addDiningRoom(new Student(PawnColor.BLUE));
        }
        for(int i=0; i<2; i++) {
            players.get(1).getSchool().addDiningRoom(new Student(PawnColor.YELLOW));
        }
        for(int i=0; i<0; i++) {
            players.get(1).getSchool().addDiningRoom(new Student(PawnColor.PINK));
        }

        assertEquals(1,players.get(1).getSchool().getProfessorTable().size());
        assertEquals(2,players.get(0).getSchool().getProfessorTable().size());
        assertEquals(redProfessor,players.get(1).getSchool().getProfessorTable().get(0));

        List<Student> students1 = new ArrayList<>();
        List<Student> students2 = new ArrayList<>();
        List<Student> studentsDiningRoom = new ArrayList<>();

        for(PawnColor color : PawnColor.values()) {
            studentsDiningRoom.addAll(gameHandler.getCurrentPlayer().getSchool().getStudentsDiningRoom(color));
        }

        for(int i=0; i<2;i++) {
            students1.add(gameHandler.getCurrentPlayer().getSchool().getEntrance().get(i));
            students2.add(studentsDiningRoom.get(i));
        }

        gameHandler.getGame().getEffectHandler().setChosenStudents1(students1);
        gameHandler.getGame().getEffectHandler().setChosenStudents2(students2);

        assertTrue(gameHandler.getCurrentPlayer().getSchool().getEntrance().containsAll(students1));
        assertTrue(studentsDiningRoom.containsAll(students2));
        assertFalse(gameHandler.getCurrentPlayer().getSchool().getEntrance().containsAll(students2));
        assertFalse(studentsDiningRoom.containsAll(students1));

        minstrel.power();

        studentsDiningRoom.clear();
        for(PawnColor color : PawnColor.values()) {
            studentsDiningRoom.addAll(gameHandler.getCurrentPlayer().getSchool().getStudentsDiningRoom(color));
        }

        assertTrue(gameHandler.getCurrentPlayer().getSchool().getEntrance().containsAll(students2));
        assertTrue(studentsDiningRoom.containsAll(students1));
        assertFalse(gameHandler.getCurrentPlayer().getSchool().getEntrance().containsAll(students1));
        assertFalse(studentsDiningRoom.containsAll(students2));

    }

    private GameHandler createGame() throws IOException, InvalidNewGameException, InvalidRulesException {
        SortedMap<String, Wizard> playerData = new TreeMap<>();
        playerData.put("Mario", Wizard.PURPLE);
        playerData.put("Luigi",Wizard.GREEN);
        String rulesJson;
        rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        GameRules gameRules = GameRules.fromJson(rulesJson);
        GameHandler gameHandler = GameCreator.createGame(playerData, gameRules);
        return gameHandler;
    }
}