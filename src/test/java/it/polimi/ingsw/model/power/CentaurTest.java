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
import it.polimi.ingsw.model.game.rules.GameRules;
import it.polimi.ingsw.server.PlayerLobby;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Christian Confalonieri
 */
class CentaurTest {

    /**
     * @author Christian Confalonieri
     */
    @Test
    void power_endPower() throws InvalidNewGameException, IOException, InvalidRulesException {
        GameHandler gameHandler = createGame();

        Centaur centaur = new Centaur(gameHandler);
        List<PowerCard> powerCards = new ArrayList<>();
        powerCards.add(centaur);
        gameHandler.getGame().setPowerCards(powerCards);

        assertEquals(PowerType.CENTAUR,centaur.getType());
        assertEquals(3,centaur.getCost());

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

        gameHandler.getGame().conquerIsland(island1);
        assertEquals(players.get(1),island1.getTowers().get(0).getOwner());

        for(int i=0;i<2;i++) {
            island1.addStudent(new Student(PawnColor.GREEN));
        }

        gameHandler.getGame().conquerIsland(island1);
        assertEquals(players.get(1),island1.getTowers().get(0).getOwner());

        centaur.power();

        gameHandler.getGame().conquerIsland(island1);
        assertEquals(players.get(0),island1.getTowers().get(0).getOwner());

        island1.addStudent(new Student(PawnColor.RED));

        gameHandler.getGame().conquerIsland(island1);
        assertEquals(4,island1.getInfluencePoints(players.get(1),gameHandler.getGame().getEffectHandler()));
        assertEquals(4,island1.getInfluencePoints(players.get(0),gameHandler.getGame().getEffectHandler()));
        assertEquals(players.get(0),island1.getTowers().get(0).getOwner());

        island1.addStudent(new Student(PawnColor.RED));

        centaur.endPower();

        gameHandler.getGame().conquerIsland(island1);
        assertEquals(5,island1.getInfluencePoints(players.get(1),gameHandler.getGame().getEffectHandler()));
        assertEquals(5,island1.getInfluencePoints(players.get(0),gameHandler.getGame().getEffectHandler()));
        assertEquals(players.get(0),island1.getTowers().get(0).getOwner());

        island1.addStudent(new Student(PawnColor.RED));

        gameHandler.getGame().conquerIsland(island1);
        assertEquals(7,island1.getInfluencePoints(players.get(1),gameHandler.getGame().getEffectHandler()));
        assertEquals(4,island1.getInfluencePoints(players.get(0),gameHandler.getGame().getEffectHandler()));
        assertEquals(players.get(1),island1.getTowers().get(0).getOwner());
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