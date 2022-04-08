package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.ServerNetworkHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {

    private static Server singleton;
    public static Server getInstance() {
        if(singleton == null) {
            singleton = new Server();
        }
        return singleton;
    }


    private int serverPort;
    private List<GameHandler> hostedGames;
    private Map<String, GameHandler> loggedPlayersInGame; // Refers to the game in which the player is logged in
    private Map<String, Player> playersInGameReference; // The player is logged in as Player@...
    private List<ClientNetworkHandler> clientConnections; // List of handlers referred to the connections
    private ServerNetworkHandler serverNetworkHandler;

    private GameController gameController;

    public List<GameHandler> getHostedGames() {
        return hostedGames;
    }

    public GameHandler getGameHandler(String playerId) {
        return loggedPlayersInGame.get(playerId);
    }
    public Player getPlayerReference(String playerId) { return playersInGameReference.get(playerId); }
    public void addClientConnection(ClientNetworkHandler clientConnection) { clientConnections.add(clientConnection); }

    private Server(int serverPort) {
        this.hostedGames = new ArrayList<>();
        this.loggedPlayersInGame = new HashMap<>();
        this.playersInGameReference = new HashMap<>();
        this.gameController = new GameController();
        this.clientConnections = new ArrayList<>();
        this.serverPort = serverPort;
    }

    private Server() {
        this(23154);
    }


    public void run() throws IOException {
        serverNetworkHandler = new ServerNetworkHandler(serverPort);
        serverNetworkHandler.run();
    }

    public GameController getGameController() {
        return gameController;
    }

    public static void main(String[] args) throws IOException {
        Server server;
        if(args.length == 0)
            server = new Server();
        else {
            server = new Server(Integer.getInteger(args[0]));
        }
        server.run();

    }
}
