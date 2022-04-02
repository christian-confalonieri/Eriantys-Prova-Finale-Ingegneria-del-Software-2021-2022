package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidNewGameException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameHandlerTest {

    @Test
    void advance() {

    }

    @Test
    void TwoPlayerMatch() {
        SortedMap<String, Wizard> playerData = new TreeMap<>();
        playerData.put("Pippo", Wizard.YELLOW);
        playerData.put("Topolino",Wizard.GREEN);

        String rulesJson = null;
        try {
            rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2Psimple.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameHandler gameHandler = null;

        try {
            gameHandler = GameCreator.createGame(playerData, rulesJson);
        } catch (InvalidNewGameException e) {
            e.printStackTrace();
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
}