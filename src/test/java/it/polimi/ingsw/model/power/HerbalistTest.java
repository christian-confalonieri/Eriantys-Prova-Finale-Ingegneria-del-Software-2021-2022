package it.polimi.ingsw.model.power;

import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.exceptions.InvalidRulesException;
import it.polimi.ingsw.model.entity.Island;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.entity.Professor;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.GameCreator;
import it.polimi.ingsw.model.game.GameHandler;
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
class HerbalistTest {

    /**
     * @author Christian Confalonieri
     */
    @Test
    void power() throws InvalidNewGameException, IOException, InvalidRulesException {
        GameHandler gameHandler = createGame();

        Herbalist herbalist = new Herbalist(gameHandler);
        List<PowerCard> powerCards = new ArrayList<>();
        powerCards.add(herbalist);
        gameHandler.getGame().setPowerCards(powerCards);

        assertEquals(PowerType.HERBALIST,herbalist.getType());
        assertEquals(2,herbalist.getCost());
        assertEquals(4,herbalist.getNoEntryCards());

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

        Island island0 = gameHandler.getGame().getIslands().get(0);
        Island island1 = gameHandler.getGame().getIslands().get(1);
        if(!island0.getStudents().isEmpty()) {
            island0.getStudents().clear();
        }
        if(!island1.getStudents().isEmpty()) {
            island1.getStudents().clear();
        }

        assertEquals(0,island1.getInfluencePoints(players.get(1),gameHandler.getGame().getEffectHandler()));
        assertEquals(0,island1.getInfluencePoints(players.get(1),gameHandler.getGame().getEffectHandler()));

        for(int i=0;i<3;i++) {
            island0.addStudent(new Student(PawnColor.RED));
        }

        for(int i=0;i<2;i++) {
            island0.addStudent(new Student(PawnColor.GREEN));
        }

        for(int i=0;i<4;i++) {
            island0.addStudent(new Student(PawnColor.PINK));
        }

        for(int i=0;i<3;i++) {
            island1.addStudent(new Student(PawnColor.RED));
        }

        for(int i=0;i<2;i++) {
            island1.addStudent(new Student(PawnColor.GREEN));
        }

        for(int i=0;i<4;i++) {
            island1.addStudent(new Student(PawnColor.PINK));
        }

        gameHandler.getGame().getEffectHandler().setChosenIsland(island1);
        herbalist.power();
        assertEquals(3,herbalist.getNoEntryCards());

        gameHandler.getGame().getMotherNature().setIsland(island0);
        gameHandler.getGame().conquerIsland(gameHandler.getGame().getMotherNature().isOn());

        assertEquals(players.get(1),gameHandler.getGame().getMotherNature().isOn().getTowers().get(0).getOwner());
        assertEquals(island0,gameHandler.getGame().getMotherNature().isOn());

        gameHandler.getGame().getMotherNature().move(1);
        assertEquals(3,herbalist.getNoEntryCards());
        gameHandler.getGame().conquerIsland(gameHandler.getGame().getMotherNature().isOn());
        assertEquals(4,herbalist.getNoEntryCards());

        assertEquals(island1,gameHandler.getGame().getMotherNature().isOn());
        assertEquals(0,gameHandler.getGame().getMotherNature().isOn().getTowers().size());

        gameHandler.getGame().conquerIsland(gameHandler.getGame().getMotherNature().isOn());

        assertEquals(island1,gameHandler.getGame().getMotherNature().isOn());
        assertEquals(1,gameHandler.getGame().getMotherNature().isOn().getTowers().size());
        assertEquals(players.get(1),gameHandler.getGame().getMotherNature().isOn().getTowers().get(0).getOwner());
    }

    private GameHandler createGame() throws IOException, InvalidNewGameException, InvalidRulesException {
        SortedMap<String, Wizard> playerData = new TreeMap<>();
        playerData.put("Mario", Wizard.PURPLE);
        playerData.put("Luigi",Wizard.GREEN);
        String rulesJson;
        rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        GameHandler gameHandler = GameCreator.createGame(playerData, rulesJson);
        return gameHandler;
    }
}