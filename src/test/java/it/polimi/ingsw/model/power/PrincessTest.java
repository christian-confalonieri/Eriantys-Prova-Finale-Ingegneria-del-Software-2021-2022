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
import it.polimi.ingsw.server.PlayerLobby;
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
class PrincessTest {

    /**
     * @author Christian Confalonieri
     */
    @Test
    void power() throws InvalidNewGameException, IOException, InvalidRulesException {
        GameHandler gameHandler = createGame();

        Princess princess = new Princess(gameHandler);
        List<PowerCard> powerCards = new ArrayList<>();
        powerCards.add(princess);
        gameHandler.getGame().setPowerCards(powerCards);

        assertEquals(PowerType.PRINCESS,princess.getType());
        assertEquals(2,princess.getCost());
        assertEquals(4,princess.getStudents().size());

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

        List<Student> studentsDiningRoom = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        students.add(princess.getStudents().get(0));
        gameHandler.getGame().getEffectHandler().setChosenStudents1(students);

        assertTrue(princess.getStudents().containsAll(students));
        for(PawnColor color : PawnColor.values()) {
            studentsDiningRoom.addAll(gameHandler.getCurrentPlayer().getSchool().getStudentsDiningRoom(color));
        }
        assertFalse(studentsDiningRoom.containsAll(students));
        assertEquals(4,princess.getStudents().size());

        princess.power();

        assertFalse(princess.getStudents().containsAll(students));
        studentsDiningRoom.clear();
        for(PawnColor color : PawnColor.values()) {
            studentsDiningRoom.addAll(gameHandler.getCurrentPlayer().getSchool().getStudentsDiningRoom(color));
        }
        assertTrue(studentsDiningRoom.containsAll(students));
        assertEquals(4,princess.getStudents().size());
    }

    private GameHandler createGame() throws IOException, InvalidNewGameException, InvalidRulesException {
        List<PlayerLobby> playerData = new ArrayList<>();
        playerData.add(new PlayerLobby("Pippo", Wizard.YELLOW));
        playerData.add(new PlayerLobby("Topolino", Wizard.GREEN));
        String rulesJson;
        rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        GameRules gameRules = GameRules.fromJson(rulesJson);
        GameHandler gameHandler = GameCreator.createGame(playerData, gameRules);
        return gameHandler;
    }
}