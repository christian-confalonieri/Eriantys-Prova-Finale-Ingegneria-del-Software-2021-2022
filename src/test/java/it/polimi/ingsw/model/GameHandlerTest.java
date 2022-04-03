package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.exceptions.InvalidRulesException;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameHandlerTest {

    @Test
    void advance() {

    }

    @Test
    void twoPlayerMatchAdvance() {
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


        Game board = gameHandler.getGame();


        assertNotNull(board);

        assertEquals(gameHandler.gamePhase, GamePhase.PREPARATION);
        assertEquals(gameHandler.currentPlayer.getName(), "Pippo");
        gameHandler.currentPlayer.playCard(Card.FOUR);
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.PREPARATION);
        assertEquals(gameHandler.currentPlayer.getName(), "Topolino");
        gameHandler.currentPlayer.playCard(Card.FIVE);
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.TURN);
        assertEquals(gameHandler.turnPhase, TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.currentPlayer.getName(), "Pippo");
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.TURN);
        assertEquals(gameHandler.turnPhase, TurnPhase.MOVEMOTHER);
        assertEquals(gameHandler.currentPlayer.getName(), "Pippo");
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.TURN);
        assertEquals(gameHandler.turnPhase, TurnPhase.MOVEFROMCLOUD);
        assertEquals(gameHandler.currentPlayer.getName(), "Pippo");
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.TURN);
        assertEquals(gameHandler.turnPhase, TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.currentPlayer.getName(), "Topolino");
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.TURN);
        assertEquals(gameHandler.turnPhase, TurnPhase.MOVEMOTHER);
        assertEquals(gameHandler.currentPlayer.getName(), "Topolino");
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.TURN);
        assertEquals(gameHandler.turnPhase, TurnPhase.MOVEFROMCLOUD);
        assertEquals(gameHandler.currentPlayer.getName(), "Topolino");
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.PREPARATION);
        assertEquals(gameHandler.currentPlayer.getName(), "Pippo");
        gameHandler.currentPlayer.playCard(Card.EIGHT);
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.PREPARATION);
        assertEquals(gameHandler.currentPlayer.getName(), "Topolino");
        gameHandler.currentPlayer.playCard(Card.THREE);
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.TURN);
        assertEquals(gameHandler.turnPhase, TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.currentPlayer.getName(), "Topolino");
        gameHandler.advance();

        gameHandler.advance();
        gameHandler.advance();

        gameHandler.advance();
        gameHandler.advance();
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.PREPARATION);
        assertEquals(gameHandler.currentPlayer.getName(), "Topolino");
        gameHandler.currentPlayer.playCard(Card.THREE);
        gameHandler.advance();
    }

    @Test
    void threePlayerMatchAdvance() {
        SortedMap<String, Wizard> playerData = new TreeMap<>();
        playerData.put("Abbate", Wizard.YELLOW);
        playerData.put("Bertoldo",Wizard.GREEN);
        playerData.put("Carnaroli", Wizard.PURPLE);

        String rulesJson = null;
        try {
            rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules3P.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameHandler gameHandler = null;

        try {
            gameHandler = GameCreator.createGame(playerData, rulesJson);
        } catch (InvalidNewGameException | InvalidRulesException e) {
            System.out.println(e.getMessage());
        }

        Game board = gameHandler.getGame();

        assertEquals(gameHandler.gamePhase, GamePhase.PREPARATION);
        assertEquals(gameHandler.currentPlayer.getName(), "Abbate");
        gameHandler.currentPlayer.playCard(Card.EIGHT);
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.PREPARATION);
        assertEquals(gameHandler.currentPlayer.getName(), "Bertoldo");
        gameHandler.currentPlayer.playCard(Card.FIVE);
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.PREPARATION);
        assertEquals(gameHandler.currentPlayer.getName(), "Carnaroli");
        gameHandler.currentPlayer.playCard(Card.NINE);
        gameHandler.advance();


        //In turno si procede in ordine di carta
        assertEquals(gameHandler.gamePhase, GamePhase.TURN);
        assertEquals(gameHandler.turnPhase, TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.currentPlayer.getName(), "Bertoldo");
        gameHandler.advance();
        gameHandler.advance();
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.TURN);
        assertEquals(gameHandler.turnPhase, TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.currentPlayer.getName(), "Abbate");
        gameHandler.advance();
        gameHandler.advance();
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.TURN);
        assertEquals(gameHandler.turnPhase, TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.currentPlayer.getName(), "Carnaroli");
        gameHandler.advance();
        gameHandler.advance();
        gameHandler.advance();


        // In preparazione si prosegue in senso orario
        assertEquals(gameHandler.gamePhase, GamePhase.PREPARATION);
        assertEquals(gameHandler.currentPlayer.getName(), "Bertoldo");
        gameHandler.currentPlayer.playCard(Card.THREE);
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.PREPARATION);
        assertEquals(gameHandler.currentPlayer.getName(), "Carnaroli");
        gameHandler.currentPlayer.playCard(Card.TWO);
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.PREPARATION);
        assertEquals(gameHandler.currentPlayer.getName(), "Abbate");
        gameHandler.currentPlayer.playCard(Card.TEN);
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.TURN);
        assertEquals(gameHandler.turnPhase, TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.currentPlayer.getName(), "Carnaroli");
        gameHandler.advance();
        assertEquals(gameHandler.gamePhase, GamePhase.TURN);
        assertEquals(gameHandler.turnPhase, TurnPhase.MOVEMOTHER);
        assertEquals(gameHandler.currentPlayer.getName(), "Carnaroli");
        gameHandler.advance();
        assertEquals(gameHandler.gamePhase, GamePhase.TURN);
        assertEquals(gameHandler.turnPhase, TurnPhase.MOVEFROMCLOUD);
        assertEquals(gameHandler.currentPlayer.getName(), "Carnaroli");
        gameHandler.advance();
    }

    @Test
    void fourPlayerMatchAdvance() {
        SortedMap<String, Wizard> playerData = new TreeMap<>();
        playerData.put("Abbate", Wizard.YELLOW);
        playerData.put("Bertoldo",Wizard.GREEN);
        playerData.put("Carnaroli", Wizard.PURPLE);
        playerData.put("Drondo", Wizard.BLUE);

        String rulesJson = null;
        try {
            rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules4P.json"))); // First player is 1
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameHandler gameHandler = null;

        try {
            gameHandler = GameCreator.createGame(playerData, rulesJson);
        } catch (InvalidNewGameException | InvalidRulesException e) {
            System.out.println(e.getMessage());
        }

        Game board = gameHandler.getGame();

        assertEquals(gameHandler.gamePhase, GamePhase.PREPARATION);
        assertEquals(gameHandler.currentPlayer.getName(), "Bertoldo");
        gameHandler.currentPlayer.playCard(Card.EIGHT);
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.PREPARATION);
        assertEquals(gameHandler.currentPlayer.getName(), "Carnaroli");
        gameHandler.currentPlayer.playCard(Card.FIVE);
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.PREPARATION);
        assertEquals(gameHandler.currentPlayer.getName(), "Drondo");
        gameHandler.currentPlayer.playCard(Card.NINE);
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.PREPARATION);
        assertEquals(gameHandler.currentPlayer.getName(), "Abbate");
        gameHandler.currentPlayer.playCard(Card.TEN);
        gameHandler.advance();


        //In turno si procede in ordine di carta
        assertEquals(gameHandler.gamePhase, GamePhase.TURN);
        assertEquals(gameHandler.turnPhase, TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.currentPlayer.getName(), "Carnaroli");
        gameHandler.advance();
        gameHandler.advance();
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.TURN);
        assertEquals(gameHandler.turnPhase, TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.currentPlayer.getName(), "Bertoldo");
        gameHandler.advance();
        gameHandler.advance();
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.TURN);
        assertEquals(gameHandler.turnPhase, TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.currentPlayer.getName(), "Drondo");
        gameHandler.advance();
        gameHandler.advance();
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.TURN);
        assertEquals(gameHandler.turnPhase, TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.currentPlayer.getName(), "Abbate");
        gameHandler.advance();
        gameHandler.advance();
        gameHandler.advance();


        // In preparazione si prosegue in senso orario
        assertEquals(gameHandler.gamePhase, GamePhase.PREPARATION);
        assertEquals(gameHandler.currentPlayer.getName(), "Carnaroli");
        gameHandler.currentPlayer.playCard(Card.THREE);
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.PREPARATION);
        assertEquals(gameHandler.currentPlayer.getName(), "Drondo");
        gameHandler.currentPlayer.playCard(Card.TWO);
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.PREPARATION);
        assertEquals(gameHandler.currentPlayer.getName(), "Abbate");
        gameHandler.currentPlayer.playCard(Card.ONE);
        gameHandler.advance();

        assertEquals(gameHandler.gamePhase, GamePhase.PREPARATION);
        assertEquals(gameHandler.currentPlayer.getName(), "Bertoldo");
        gameHandler.currentPlayer.playCard(Card.FOUR);
        gameHandler.advance();


        assertEquals(gameHandler.gamePhase, GamePhase.TURN);
        assertEquals(gameHandler.turnPhase, TurnPhase.MOVESTUDENTS);
        assertEquals(gameHandler.currentPlayer.getName(), "Abbate");
        gameHandler.advance();
        assertEquals(gameHandler.gamePhase, GamePhase.TURN);
        assertEquals(gameHandler.turnPhase, TurnPhase.MOVEMOTHER);
        assertEquals(gameHandler.currentPlayer.getName(), "Abbate");
        gameHandler.advance();
        assertEquals(gameHandler.gamePhase, GamePhase.TURN);
        assertEquals(gameHandler.turnPhase, TurnPhase.MOVEFROMCLOUD);
        assertEquals(gameHandler.currentPlayer.getName(), "Abbate");
        gameHandler.advance();
    }

    @Test
    void checkEndGameWithOneTurnSimulation() {
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

        // Pippo plays card
        gameHandler.currentPlayer.playCard(Card.THREE);
        gameHandler.advance();

        // Topolino plays card
        gameHandler.currentPlayer.playCard(Card.FIVE);
        gameHandler.advance();

        // Pippo moves 3 student MOVESTUDENTS
        assertEquals(gameHandler.currentPlayer.getName(), "Pippo");
        Student s1 = gameHandler.currentPlayer.getSchool().getEntrance().get(0);
        Student s2 = gameHandler.currentPlayer.getSchool().getEntrance().get(1);
        Student s3 = gameHandler.currentPlayer.getSchool().getEntrance().get(2);
        gameHandler.currentPlayer.getSchool().entranceToDiningRoom(s1);
        gameHandler.currentPlayer.getSchool().entranceToDiningRoom(s2);
        gameHandler.currentPlayer.getSchool().entranceToIsland(s3, gameHandler.game.islands.get(1));

        gameHandler.game.professorRelocate(); // Pippo earnes professors
        assertTrue(gameHandler.currentPlayer.getSchool().getProfessorTable().size() >= 1);
        gameHandler.advance();

        // Pippo moves mothernature MOVEMOTHER
        gameHandler.game.motherNature.move(1);
        // IF NOT NULL
        Player influenceOnIsland = gameHandler.game.motherNature.isOn().getInfluencePlayer(gameHandler.game.players, gameHandler.game.effectHandler);
        influenceOnIsland.getSchool().moveTower(gameHandler.game.motherNature.isOn()); // Moves all the tower on so wins at the end of this turn
        influenceOnIsland.getSchool().moveTower(gameHandler.game.motherNature.isOn());
        influenceOnIsland.getSchool().moveTower(gameHandler.game.motherNature.isOn());
        influenceOnIsland.getSchool().moveTower(gameHandler.game.motherNature.isOn());
        influenceOnIsland.getSchool().moveTower(gameHandler.game.motherNature.isOn());
        influenceOnIsland.getSchool().moveTower(gameHandler.game.motherNature.isOn());
        influenceOnIsland.getSchool().moveTower(gameHandler.game.motherNature.isOn());
        influenceOnIsland.getSchool().moveTower(gameHandler.game.motherNature.isOn());

        if (gameHandler.game.motherNature.isOn().checkUnifyNext())
            gameHandler.game.unifyIsland(gameHandler.game.motherNature.isOn(), gameHandler.game.motherNature.isOn().getNextIsland());
        if (gameHandler.game.motherNature.isOn().checkUnifyPrev())
            gameHandler.game.unifyIsland(gameHandler.game.motherNature.isOn(), gameHandler.game.motherNature.isOn().getPrevIsland());

        gameHandler.advance();

        // Pippo choses an Cloud MOVECLOUD
        List<Student> pickedCloud = gameHandler.game.clouds.get(0).pickAllStudents();
        for (Student student : pickedCloud)
            gameHandler.currentPlayer.getSchool().addEntrance(student);

        gameHandler.advance();

        assertTrue(gameHandler.isEnded());
        List<Player> leaderBoard = gameHandler.getGame().getLeaderBoard();
        Player winner = leaderBoard.get(0);

        assertEquals(winner, influenceOnIsland);
    }
}