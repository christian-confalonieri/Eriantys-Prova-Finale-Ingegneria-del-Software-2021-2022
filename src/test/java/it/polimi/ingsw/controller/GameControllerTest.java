package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.action.*;
import it.polimi.ingsw.client.TestClient;
import it.polimi.ingsw.exceptions.InvalidRulesException;
import it.polimi.ingsw.model.entity.Island;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.Card;
import it.polimi.ingsw.model.enumeration.GamePhase;
import it.polimi.ingsw.model.enumeration.PawnColor;
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
import java.util.*;

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

    @Test
    void PlayCardAction() throws IOException, InvalidRulesException, InterruptedException {
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

        client1.send(ActionHandler.toJson(new PlayCardAction(playerId1, Card.FOUR))); // leo plays a card
        Thread.sleep(1000);
        assertEquals(gameHandler.getGame().getPlayers().get(0).getLastPlayedCard(), Card.FOUR);

        client2.send(ActionHandler.toJson(new PlayCardAction(playerId2, Card.ONE))); // Giuseppe plays a card
        Thread.sleep(1000);
        assertEquals(gameHandler.getGame().getPlayers().get(1).getLastPlayedCard(), Card.ONE);

        assertEquals(gameHandler.getCurrentPlayer(), Server.getInstance().getInGamePlayer(playerId2)); // Giuseppe should be first to play the turn


        List<Student> entrance = Server.getInstance().getInGamePlayer(playerId2).getSchool().getEntrance(); // Simulation of a serialization-deserialization
        Gson gson = new Gson();
        String entranceSerialized = gson.toJson(entrance);
        JsonArray jsonArray = JsonParser.parseString(entranceSerialized).getAsJsonArray();
        List<Student> entranceDeserialized = new ArrayList<>();
        for(int i = 0; i < jsonArray.size(); i++) {
            entranceDeserialized.add(gson.fromJson(jsonArray.get(i), Student.class));
        }
        assertEquals(entrance, entranceDeserialized);

        // List<Student> entranceDeSerialized = gson.fromJson(entranceSerialized, )

        // client2.send(ActionHandler.toJson(new MoveStudentsAction(playerId2, )));
    }

    @Test
    void TwoPlayerGameTurnPhase() throws IOException, InvalidRulesException, InterruptedException {
        // Game Start (already tested) --------------------------------------------------------------------------------------------------------------

        String[] args = {};
        new Thread(new Runnable() {
            @Override
            public void run() {
                Server.main(args);
            }
        }).start(); // Starts a thread with the server

        TestClient client1 = new TestClient("localhost", 23154);
        String playerId1 = "LeonardoA00";
        Scanner serverReplyClient1 = client1.getInputScanner();

        TestClient client2 = new TestClient("localhost", 23154);
        String playerId2 = "CON+F4";
        Scanner serverReplyClient2 = client2.getInputScanner();

        client1.send(ActionHandler.toJson(new LoginAction(playerId1)));
        Thread.sleep(1000);
        String response1 = serverReplyClient1.next();

        client2.send(ActionHandler.toJson(new LoginAction(playerId2)));
        Thread.sleep(1000);
        String response2 = serverReplyClient2.next();

        String rulesFile = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        GameRules gameRules = GameRules.fromJson(rulesFile);

        client1.send(ActionHandler.toJson(new NewGameAction(playerId1, 2, gameRules, Wizard.BLUE)));

        Thread.sleep(1000);

        String lobbyId = serverReplyClient1.next();
        System.out.println(lobbyId);

        client2.send(ActionHandler.toJson(new JoinGameAction(playerId2, lobbyId, Wizard.YELLOW)));
        Thread.sleep(1000);

        GameHandler gameHandler = Server.getInstance().getGameHandler(playerId1);

        // TURN PHASE TESTING: --------------------------------------------------------------------------------------------------------------------

        client1.send(ActionHandler.toJson(new PlayCardAction(playerId1, Card.FOUR))); // player1 plays a card
        Thread.sleep(1000);
        assertEquals(gameHandler.getGame().getPlayers().get(0).getLastPlayedCard(), Card.FOUR);

        client2.send(ActionHandler.toJson(new PlayCardAction(playerId2, Card.ONE))); // player2 plays a card
        Thread.sleep(1000);
        assertEquals(gameHandler.getGame().getPlayers().get(1).getLastPlayedCard(), Card.ONE);

        assertEquals(gameHandler.getCurrentPlayer(), Server.getInstance().getInGamePlayer(playerId2)); // player2 should be first to play the turn


        List<Student> toDiningRoom = new ArrayList<>();
        toDiningRoom.add(Server.getInstance().getInGamePlayer(playerId2).getSchool().getEntrance().get(0));
        Island island1 = Server.getInstance().getGameHandler(playerId2).getGame().getIslands().get(0);
        Island island2 = Server.getInstance().getGameHandler(playerId2).getGame().getIslands().get(1);
        Map<Student, Island> toIsland =  new HashMap<>();
        toIsland.put(Server.getInstance().getInGamePlayer(playerId2).getSchool().getEntrance().get(1),island1);
        toIsland.put(Server.getInstance().getInGamePlayer(playerId2).getSchool().getEntrance().get(2),island2);

        assertTrue(Server.getInstance().getInGamePlayer(playerId2).getSchool().getEntrance().containsAll(toDiningRoom));
        assertTrue(Server.getInstance().getInGamePlayer(playerId2).getSchool().getEntrance().containsAll(toIsland.keySet()));

        List<Student> tempStudentsDR = new ArrayList<>();
        for(PawnColor color : PawnColor.values())  {
            tempStudentsDR.addAll(Server.getInstance().getInGamePlayer(playerId2).getSchool().getStudentsDiningRoom(color));
        }
        List<Student> tempStudentsIS = new ArrayList<>();
        tempStudentsIS.addAll(island1.getStudents());
        tempStudentsIS.addAll(island2.getStudents());

        assertFalse(tempStudentsDR.containsAll(toDiningRoom));
        assertFalse(tempStudentsIS.containsAll(toIsland.keySet()));

        MoveStudentsAction moveStudentsAction = new MoveStudentsAction(playerId2,toDiningRoom,toIsland);
        String toJson = ActionHandler.toJson(moveStudentsAction);
        client2.send(toJson); // player 2 moves the students from the entrance to the dining room and islands
        Thread.sleep(1000);


    }
}