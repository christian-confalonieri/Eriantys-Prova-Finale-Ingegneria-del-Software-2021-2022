package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.exceptions.InvalidRulesException;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.entity.School;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameCreator;
import it.polimi.ingsw.model.game.GameHandler;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void unifyIslandInTheMiddle() {
        SortedMap<String, Wizard> playerData = new TreeMap<>();
        playerData.put("Pippo", Wizard.YELLOW);
        playerData.put("Topolino",Wizard.GREEN);

        String rulesJson = null;
        try {
            rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameHandler gameHandler = null;

        try {
            gameHandler = GameCreator.createGame(playerData, rulesJson);
        } catch (InvalidNewGameException | InvalidRulesException e) {
            e.printStackTrace();
        }


        Game game = gameHandler.getGame();

        School pippoSchool = game.players.get(0).getSchool();
        assertEquals(pippoSchool.getTowers().size(), 8);
        pippoSchool.moveTower(game.islands.get(4));
        pippoSchool.moveTower(game.islands.get(5));
        assertEquals(pippoSchool.getTowers().size(), 6);
        assertFalse(game.islands.get(4).checkUnifyPrev());
        assertFalse(game.islands.get(5).checkUnifyNext());
        assertTrue(game.islands.get(4).checkUnifyNext());
        assertTrue(game.islands.get(5).checkUnifyPrev());

        game.unifyIsland(game.islands.get(5), game.islands.get(4));
        assertEquals(game.islands.size(), 11);
        assertEquals(game.motherNature.isOn().getIslandSize(), 2);
    }

    @Test
    void unifyIslandInBorder() {
        SortedMap<String, Wizard> playerData = new TreeMap<>();
        playerData.put("Pippo", Wizard.YELLOW);
        playerData.put("Topolino",Wizard.GREEN);

        String rulesJson = null;
        try {
            rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameHandler gameHandler = null;

        try {
            gameHandler = GameCreator.createGame(playerData, rulesJson);
        } catch (InvalidNewGameException | InvalidRulesException e) {
            e.printStackTrace();
        }


        Game game = gameHandler.getGame();

        School pippoSchool = game.players.get(0).getSchool();
        assertEquals(pippoSchool.getTowers().size(), 8);
        pippoSchool.moveTower(game.islands.get(0));
        pippoSchool.moveTower(game.islands.get(11));
        assertEquals(pippoSchool.getTowers().size(), 6);
        assertFalse(game.islands.get(11).checkUnifyPrev());
        assertFalse(game.islands.get(0).checkUnifyNext());
        assertTrue(game.islands.get(11).checkUnifyNext());
        assertTrue(game.islands.get(0).checkUnifyPrev());

        game.unifyIsland(game.islands.get(0), game.islands.get(11));
        assertEquals(game.islands.size(), 11);
        assertEquals(game.motherNature.isOn().getIslandSize(), 2);
    }

    @Test
    void unifyIslandTriple() {
        SortedMap<String, Wizard> playerData = new TreeMap<>();
        playerData.put("Pippo", Wizard.YELLOW);
        playerData.put("Topolino",Wizard.GREEN);

        String rulesJson = null;
        try {
            rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameHandler gameHandler = null;

        try {
            gameHandler = GameCreator.createGame(playerData, rulesJson);
        } catch (InvalidNewGameException | InvalidRulesException e) {
            e.printStackTrace();
        }


        Game game = gameHandler.getGame();

        School pippoSchool = game.players.get(0).getSchool();
        assertEquals(pippoSchool.getTowers().size(), 8);
        pippoSchool.moveTower(game.islands.get(1));
        pippoSchool.moveTower(game.islands.get(11));
        assertEquals(pippoSchool.getTowers().size(), 6);
        // Pippo puts a tower in island0, in between other two islands
        pippoSchool.moveTower((game.islands.get(0)));
        assertTrue(game.islands.get(0).checkUnifyNext());
        game.unifyIsland(game.islands.get(0), game.islands.get(0).getNextIsland());
        assertFalse(game.motherNature.isOn().checkUnifyNext());
        assertTrue(game.motherNature.isOn().checkUnifyPrev());
        game.unifyIsland(game.motherNature.isOn(), game.motherNature.isOn().getPrevIsland());

        assertEquals(game.islands.size(), 10);
        assertEquals(game.motherNature.isOn().getIslandSize(), 3);
    }

    @Test
    void professorRelocate() {
        SortedMap<String, Wizard> playerData = new TreeMap<>();
        playerData.put("Pippo", Wizard.YELLOW);
        playerData.put("Topolino",Wizard.GREEN);

        String rulesJson = null;
        try {
            rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameHandler gameHandler = null;

        try {
            gameHandler = GameCreator.createGame(playerData, rulesJson);
        } catch (InvalidNewGameException | InvalidRulesException e) {
            System.out.println(e.getMessage());
        }

        Player pippo = gameHandler.game.players.get(0);
        pippo.getSchool().addDiningRoom(new Student(PawnColor.RED));
        pippo.getSchool().addDiningRoom(new Student(PawnColor.BLUE));
        pippo.getSchool().addDiningRoom(new Student(PawnColor.YELLOW));
        pippo.getSchool().addDiningRoom(new Student(PawnColor.YELLOW));
        pippo.getSchool().addDiningRoom(new Student(PawnColor.YELLOW));

        gameHandler.game.professorRelocate();

        assertTrue(pippo.getSchool().hasProfessor(PawnColor.RED));
        assertTrue(pippo.getSchool().hasProfessor(PawnColor.BLUE));
        assertTrue(pippo.getSchool().hasProfessor(PawnColor.YELLOW));
        assertEquals(pippo.getSchool().getProfessorTable().size(), 3);


        Player topolino = gameHandler.game.players.get(1);
        topolino.getSchool().addDiningRoom(new Student(PawnColor.RED));
        topolino.getSchool().addDiningRoom(new Student(PawnColor.YELLOW));
        topolino.getSchool().addDiningRoom(new Student(PawnColor.PINK));

        gameHandler.game.professorRelocate();

        assertTrue(pippo.getSchool().hasProfessor(PawnColor.RED));
        assertTrue(pippo.getSchool().hasProfessor(PawnColor.BLUE));
        assertTrue(pippo.getSchool().hasProfessor(PawnColor.YELLOW));
        assertEquals(pippo.getSchool().getProfessorTable().size(), 3);
        assertTrue(topolino.getSchool().hasProfessor(PawnColor.PINK));

        pippo.getSchool().addDiningRoom(new Student(PawnColor.PINK));
        pippo.getSchool().addDiningRoom((new Student(PawnColor.PINK)));

        gameHandler.game.professorRelocate();

        assertTrue(pippo.getSchool().hasProfessor(PawnColor.RED));
        assertTrue(pippo.getSchool().hasProfessor(PawnColor.BLUE));
        assertTrue(pippo.getSchool().hasProfessor(PawnColor.YELLOW));
        assertTrue(pippo.getSchool().hasProfessor((PawnColor.PINK)));
        assertEquals(pippo.getSchool().getProfessorTable().size(), 4);
        assertEquals(topolino.getSchool().getProfessorTable().size(), 0);

    }

    @Test
    void refillClouds() {
    }

    @Test
    void checkEndGame() {
    }
}