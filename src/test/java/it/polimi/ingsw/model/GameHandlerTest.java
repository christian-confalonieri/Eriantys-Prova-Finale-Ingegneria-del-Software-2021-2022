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
    void TwoPlayerMatchAdvance() {
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
    void ThreePlayerMatchAdvance() {
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
    void FourPlayerMatchAdvance() {
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
}