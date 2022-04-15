package it.polimi.ingsw.controller;

import it.polimi.ingsw.action.JoinGameAction;
import it.polimi.ingsw.action.LoginAction;
import it.polimi.ingsw.action.NewGameAction;
import it.polimi.ingsw.client.TestClient;
import it.polimi.ingsw.exceptions.InvalidRulesException;
import it.polimi.ingsw.model.enumeration.GamePhase;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.game.rules.GameRules;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.server.Server;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    @Test
    void TwoPlayerGameStart() throws IOException, InvalidRulesException, InterruptedException {
        String[] args = {};
        new Thread(new Runnable() {
            @Override
            public void run() {
                Server.main(args);
            }
        }).start(); // Starts a thread with the server

        TestClient client1 = new TestClient("localhost", 23154);
        String playerId1 = "leonardoa00";
        Scanner serverReplyClient1 = client1.getInputScanner();

        TestClient client2 = new TestClient("localhost", 23154);
        String playerId2 = "xXGiuseppino3245Xx";
        Scanner serverReplyClient2 = client2.getInputScanner();

        client1.send(ActionHandler.toJson(new LoginAction(playerId1)));
        Thread.sleep(1000);
        assertTrue(Server.getInstance().isAssigned(playerId1)); // Verifica se player1 loggato
        assertFalse(Server.getInstance().isAssigned(playerId2));
        assertTrue(serverReplyClient1.hasNext());
        String response1 = serverReplyClient1.next();

        client2.send(ActionHandler.toJson(new LoginAction(playerId2)));
        Thread.sleep(1000);
        assertTrue(Server.getInstance().isAssigned(playerId1));
        assertTrue(Server.getInstance().isAssigned(playerId2)); // Verifica se player2 loggato
        assertTrue(serverReplyClient2.hasNext());
        String response2 = serverReplyClient2.next();

        String rulesFile = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        GameRules gameRules = GameRules.fromJson(rulesFile);

        client1.send(ActionHandler.toJson(new NewGameAction(playerId1, 2, gameRules, Wizard.BLUE)));

        Thread.sleep(1000);
        assertTrue(serverReplyClient1.hasNext());

        String lobbyId = serverReplyClient1.next();
        System.out.println(lobbyId);

        client2.send(ActionHandler.toJson(new JoinGameAction(playerId2, lobbyId, Wizard.YELLOW)));
        Thread.sleep(1000);
        // Game should be started
        assertEquals(Server.getInstance().getAllGameLobbys().size(), 0);

        assertEquals(Server.getInstance().getAllHostedGames().size(), 1);

        assertEquals(Server.getInstance().getGameHandler(playerId1), Server.getInstance().getGameHandler(playerId2));

        assertEquals(Server.getInstance().getInGamePlayer(playerId1).getName(), playerId1);
        assertEquals(Server.getInstance().getInGamePlayer(playerId2).getName(), playerId2);

        // get the gameHandler of the 2 playing
        GameHandler gameHandler = Server.getInstance().getGameHandler(playerId1);
        assertEquals(gameHandler.getCurrentPlayer(), Server.getInstance().getInGamePlayer(playerId1)); // the first one to connect in the lobby should be first
        assertEquals(gameHandler.getGamePhase(), GamePhase.PREPARATION);
    }
}