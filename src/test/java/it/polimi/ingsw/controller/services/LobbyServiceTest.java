package it.polimi.ingsw.controller.services;

import it.polimi.ingsw.action.GetGameAction;
import it.polimi.ingsw.action.LoginAction;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.TestClient;
import it.polimi.ingsw.controller.ActionHandler;
import it.polimi.ingsw.exceptions.InvalidAction;
import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.exceptions.InvalidRulesException;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.rules.GameRules;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.server.GameLobby;
import it.polimi.ingsw.server.Server;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class LobbyServiceTest {

    @Test
    void getGame() throws IOException, InvalidRulesException, InvalidNewGameException, InvalidAction, InterruptedException {
        String[] args = {};
        new Thread(() -> Server.main(args)).start(); // Starts a thread with the server

        Client client1 = new Client("localhost", 23154);
        Scanner serverReplyClient1 = client1.getInputScanner();
        client1.send(ActionHandler.toJson(new LoginAction("leo")));
        Thread.sleep(1000);
        assertEquals(serverReplyClient1.next(), "Logged In");

        GameLobby lb = new GameLobby(GameRules.fromJson(new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")))), 2);
        lb.addPlayer("leo", Wizard.BLUE);
        lb.addPlayer("pippo", Wizard.GREEN);
        Server.getInstance().addNewGameLobby(lb);
        Server.getInstance().startGame(lb);

        LobbyService.getGame(new GetGameAction("leo"));
        String json = serverReplyClient1.next();
        client1.parseGameHandler(json);
        assertEquals(1, 1);
    }
}