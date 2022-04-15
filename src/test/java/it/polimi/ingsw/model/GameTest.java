package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.exceptions.InvalidRulesException;
import it.polimi.ingsw.model.entity.*;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameCreator;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.game.rules.GameRules;
import it.polimi.ingsw.server.PlayerLobby;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void unifyIslandInTheMiddle() throws InvalidRulesException, InvalidNewGameException {
        List<PlayerLobby> playerData = new ArrayList<>();
        playerData.add(new PlayerLobby("Pippo", Wizard.YELLOW));
        playerData.add(new PlayerLobby("Topolino", Wizard.GREEN));

        String rulesJson = null;
        try {
            rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameHandler gameHandler = null;


        GameRules gameRules = GameRules.fromJson(rulesJson);
        gameHandler = GameCreator.createGame(playerData, gameRules);



        Game game = gameHandler.getGame();

        School pippoSchool = game.getPlayers().get(0).getSchool();
        assertEquals(pippoSchool.getTowers().size(), 8);
        pippoSchool.moveTower(game.getIslands().get(4));
        pippoSchool.moveTower(game.getIslands().get(5));
        assertEquals(pippoSchool.getTowers().size(), 6);
        assertFalse(game.getIslands().get(4).checkUnifyPrev());
        assertFalse(game.getIslands().get(5).checkUnifyNext());
        assertTrue(game.getIslands().get(4).checkUnifyNext());
        assertTrue(game.getIslands().get(5).checkUnifyPrev());

        game.unifyIsland(game.getIslands().get(5), game.getIslands().get(4));
        assertEquals(game.getIslands().size(), 11);
        assertEquals(game.getMotherNature().isOn().getIslandSize(), 2);
    }

    @Test
    void unifyIslandInBorder() throws InvalidRulesException, InvalidNewGameException {
        List<PlayerLobby> playerData = new ArrayList<>();
        playerData.add(new PlayerLobby("Pippo", Wizard.YELLOW));
        playerData.add(new PlayerLobby("Topolino", Wizard.GREEN));

        String rulesJson = null;
        try {
            rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameHandler gameHandler = null;

        GameRules gameRules = GameRules.fromJson(rulesJson);
        gameHandler = GameCreator.createGame(playerData, gameRules);


        Game game = gameHandler.getGame();

        School pippoSchool = game.getPlayers().get(0).getSchool();
        assertEquals(pippoSchool.getTowers().size(), 8);
        pippoSchool.moveTower(game.getIslands().get(0));
        pippoSchool.moveTower(game.getIslands().get(11));
        assertEquals(pippoSchool.getTowers().size(), 6);
        assertFalse(game.getIslands().get(11).checkUnifyPrev());
        assertFalse(game.getIslands().get(0).checkUnifyNext());
        assertTrue(game.getIslands().get(11).checkUnifyNext());
        assertTrue(game.getIslands().get(0).checkUnifyPrev());

        game.unifyIsland(game.getIslands().get(0), game.getIslands().get(11));
        assertEquals(game.getIslands().size(), 11);
        assertEquals(game.getMotherNature().isOn().getIslandSize(), 2);
    }

    @Test
    void unifyIslandTriple() throws InvalidRulesException, InvalidNewGameException {
        List<PlayerLobby> playerData = new ArrayList<>();
        playerData.add(new PlayerLobby("Pippo", Wizard.YELLOW));
        playerData.add(new PlayerLobby("Topolino", Wizard.GREEN));

        String rulesJson = null;
        try {
            rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameHandler gameHandler = null;

        GameRules gameRules = GameRules.fromJson(rulesJson);
        gameHandler = GameCreator.createGame(playerData, gameRules);


        Game game = gameHandler.getGame();

        School pippoSchool = game.getPlayers().get(0).getSchool();
        assertEquals(pippoSchool.getTowers().size(), 8);
        pippoSchool.moveTower(game.getIslands().get(1));
        pippoSchool.moveTower(game.getIslands().get(11));
        assertEquals(pippoSchool.getTowers().size(), 6);
        // Pippo puts a tower in island0, in between other two islands
        pippoSchool.moveTower((game.getIslands().get(0)));
        assertTrue(game.getIslands().get(0).checkUnifyNext());
        game.unifyIsland(game.getIslands().get(0), game.getIslands().get(0).getNextIsland());
        assertFalse(game.getMotherNature().isOn().checkUnifyNext());
        assertTrue(game.getMotherNature().isOn().checkUnifyPrev());
        game.unifyIsland(game.getMotherNature().isOn(), game.getMotherNature().isOn().getPrevIsland());

        assertEquals(game.getIslands().size(), 10);
        assertEquals(game.getMotherNature().isOn().getIslandSize(), 3);
    }

    @Test
    void professorRelocate() throws InvalidRulesException, InvalidNewGameException {
        List<PlayerLobby> playerData = new ArrayList<>();
        playerData.add(new PlayerLobby("Pippo", Wizard.YELLOW));
        playerData.add(new PlayerLobby("Topolino", Wizard.GREEN));

        String rulesJson = null;
        try {
            rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameHandler gameHandler = null;

        GameRules gameRules = GameRules.fromJson(rulesJson);
        gameHandler = GameCreator.createGame(playerData, gameRules);

        Player pippo = gameHandler.getGame().getPlayers().get(0);
        pippo.getSchool().addDiningRoom(new Student(PawnColor.RED));
        pippo.getSchool().addDiningRoom(new Student(PawnColor.BLUE));
        pippo.getSchool().addDiningRoom(new Student(PawnColor.YELLOW));
        pippo.getSchool().addDiningRoom(new Student(PawnColor.YELLOW));
        pippo.getSchool().addDiningRoom(new Student(PawnColor.YELLOW));

        gameHandler.getGame().professorRelocate();

        assertTrue(pippo.getSchool().hasProfessor(PawnColor.RED));
        assertTrue(pippo.getSchool().hasProfessor(PawnColor.BLUE));
        assertTrue(pippo.getSchool().hasProfessor(PawnColor.YELLOW));
        assertEquals(pippo.getSchool().getProfessorTable().size(), 3);


        Player topolino = gameHandler.getGame().getPlayers().get(1);
        topolino.getSchool().addDiningRoom(new Student(PawnColor.RED));
        topolino.getSchool().addDiningRoom(new Student(PawnColor.YELLOW));
        topolino.getSchool().addDiningRoom(new Student(PawnColor.PINK));

        gameHandler.getGame().professorRelocate();

        assertTrue(pippo.getSchool().hasProfessor(PawnColor.RED));
        assertTrue(pippo.getSchool().hasProfessor(PawnColor.BLUE));
        assertTrue(pippo.getSchool().hasProfessor(PawnColor.YELLOW));
        assertEquals(pippo.getSchool().getProfessorTable().size(), 3);
        assertTrue(topolino.getSchool().hasProfessor(PawnColor.PINK));

        pippo.getSchool().addDiningRoom(new Student(PawnColor.PINK));
        pippo.getSchool().addDiningRoom((new Student(PawnColor.PINK)));

        gameHandler.getGame().professorRelocate();

        assertTrue(pippo.getSchool().hasProfessor(PawnColor.RED));
        assertTrue(pippo.getSchool().hasProfessor(PawnColor.BLUE));
        assertTrue(pippo.getSchool().hasProfessor(PawnColor.YELLOW));
        assertTrue(pippo.getSchool().hasProfessor((PawnColor.PINK)));
        assertEquals(pippo.getSchool().getProfessorTable().size(), 4);
        assertEquals(topolino.getSchool().getProfessorTable().size(), 0);

    }

    @Test
    void getLeaderBoard() throws InvalidRulesException, InvalidNewGameException {
        List<PlayerLobby> playerData = new ArrayList<>();
        playerData.add(new PlayerLobby("Pippo", Wizard.YELLOW));
        playerData.add(new PlayerLobby("Topolino", Wizard.GREEN));

        String rulesJson = null;
        try {
            rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameHandler gameHandler = null;

        GameRules gameRules = GameRules.fromJson(rulesJson);
        gameHandler = GameCreator.createGame(playerData, gameRules);

        Island island = gameHandler.getGame().getIslands().get(0);
        Player pippo = gameHandler.getGame().getPlayers().get(0);
        Player topolino = gameHandler.getGame().getPlayers().get(1);

        topolino.getSchool().moveTower(island);
        topolino.getSchool().moveTower(island);
        pippo.getSchool().addProfessor(new Professor(PawnColor.GREEN));
        pippo.getSchool().addProfessor(new Professor(PawnColor.PINK));
        pippo.getSchool().addProfessor(new Professor(PawnColor.BLUE));

        Player winner = gameHandler.getGame().getLeaderBoard().get(0);
        assertEquals(winner, topolino);
    }

    @Test
    void getLeaderBoardEqualTowers() throws InvalidRulesException, InvalidNewGameException {
        List<PlayerLobby> playerData = new ArrayList<>();
        playerData.add(new PlayerLobby("Pippo", Wizard.YELLOW));
        playerData.add(new PlayerLobby("Topolino", Wizard.GREEN));

        String rulesJson = null;
        try {
            rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameHandler gameHandler = null;

        GameRules gameRules = GameRules.fromJson(rulesJson);
        gameHandler = GameCreator.createGame(playerData, gameRules);

        Island island = gameHandler.getGame().getIslands().get(0);
        Player pippo = gameHandler.getGame().getPlayers().get(0);
        Player topolino = gameHandler.getGame().getPlayers().get(1);

        topolino.getSchool().moveTower(island);
        topolino.getSchool().moveTower(island);
        pippo.getSchool().moveTower(island.getNextIsland());
        pippo.getSchool().moveTower(island.getNextIsland());

        pippo.getSchool().addProfessor(new Professor(PawnColor.GREEN));
        pippo.getSchool().addProfessor(new Professor(PawnColor.PINK));
        pippo.getSchool().addProfessor(new Professor(PawnColor.BLUE));
        topolino.getSchool().addProfessor(new Professor(PawnColor.YELLOW));
        topolino.getSchool().addProfessor(new Professor(PawnColor.RED));

        Player winner = gameHandler.getGame().getLeaderBoard().get(0);
        assertEquals(winner, pippo);
    }

    @Test
    void refillClouds() {
    }

    @Test
    void checkEndGame() {
    }
}