package it.polimi.ingsw.server;

import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.GameCreator;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.ServerNetworkHandler;

import java.io.Console;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Server {

    private static Server singleton;
    public static Server getInstance() {
        return singleton;
    }

    private final int serverPort;

    private final List<GameLobby> lobbyGames; // gameId to players waiting to play
    private final List<GameHandler> hostedGames;

    // Logged players in game
    private final Map<String, GameHandler> loggedPlayersInGame; // Refers to the game in which the player is logged in
    private final Map<String, Player> playersInGameReference; // The player is logged in as Player@...

    // Id to net handler bound
    private final Map<String, ClientNetworkHandler> assignedConnections;


    private final List<ClientNetworkHandler> clientConnections; // List of handlers referred to the connections

    private final GameController gameController;

    public void addNewGameLobby(GameLobby gameLobby) { lobbyGames.add(gameLobby); }
    public void startGame(GameLobby gameLobby) throws InvalidNewGameException {
        if(gameLobby.canStart()) {
            lobbyGames.remove(gameLobby);
            SortedMap<String, Wizard> playerMap = new TreeMap<>();
            for(String playerId : gameLobby.getPlayersWaiting()) {
                playerMap.put(gameLobby.getInGameName(playerId), gameLobby.getWizard(playerId));
            }
            GameHandler newGame = GameCreator.createGame(playerMap, gameLobby.getGameRules());
            addGame(newGame);
            int i = 0;
            for(String playerId : gameLobby.getPlayersWaiting()) {
                addPlayerInGame(playerId, newGame, newGame.getGame().getPlayers().get(i));
                i++;
            }
        }
    }
    public List<GameLobby> getAllGameLobbys() { return lobbyGames; }
    public GameLobby getGameLobby(String lobbyId) {
        return lobbyGames.stream().filter(lb -> lb.getGameLobbyId().equals(lobbyId)).findAny().orElse(null);
    }
    public void exitLobbys(String playerId) {
        List<GameLobby> lobbyInGame = lobbyGames.stream().filter(l -> l.isPlayerWaiting(playerId)).toList();
        for(GameLobby lb : lobbyInGame) {
            lb.removePlayer(playerId);
            if (lb.isEmpty()) { lobbyGames.remove(lb); } // Removes if the lobby has become empty
        }
    }
    public boolean isWaitingInALobby(String playerId) {
        return lobbyGames.stream().anyMatch(l -> l.isPlayerWaiting(playerId));
    }
    public List<GameHandler> getAllHostedGames() {
        return hostedGames;
    }

    public GameHandler getGameHandler(String playerId) {
        return loggedPlayersInGame.get(playerId);
    }
    public Player getInGamePlayer(String playerId) { return playersInGameReference.get(playerId); }
    public ClientNetworkHandler getClientNetHandler(String playerId) { return assignedConnections.get(playerId); }

    public void addClientConnection(ClientNetworkHandler clientConnection) {
        clientConnections.add(clientConnection);
    }
    public void removeClientConnection(ClientNetworkHandler clientConnection) { clientConnections.remove(clientConnection); }

    public void assignConnection(String playerId, ClientNetworkHandler networkHandler) {
        assignedConnections.put(playerId, networkHandler);
    }
    public void unassignConnection(String playerId) { assignedConnections.remove(playerId); }
    public boolean isAssigned(String playerId) { return assignedConnections.containsKey(playerId); }
    public boolean isAssigned(ClientNetworkHandler clientNetworkHandler) { return assignedConnections.containsValue(clientNetworkHandler); }

    public void addPlayerInGame(String playerId, GameHandler game, Player player) {
        loggedPlayersInGame.put(playerId, game);
        playersInGameReference.put(playerId, player);
    }
    public void removePlayerFromGame(String playerId) {
        loggedPlayersInGame.remove(playerId);
        playersInGameReference.remove(playerId);
    }
    public boolean isInGame(String playerId) {
        return loggedPlayersInGame.containsKey(playerId);
    }

    public List<ClientNetworkHandler> getConnectionsForGameBroadcast(GameHandler gameHandler) {
        List<String> playerIds = loggedPlayersInGame.keySet().stream().filter(id -> loggedPlayersInGame.get(id).equals(gameHandler)).toList();
        List<ClientNetworkHandler> netConnections = playerIds.stream().map(this::getClientNetHandler).toList();
        return netConnections;
    }

    public void addGame(GameHandler gameHandler) { hostedGames.add(gameHandler); }
    public void removeGame(GameHandler gameHandler) { hostedGames.remove(gameHandler); }

    public GameController getGameController() {
        return gameController;
    }




    private Server(int serverPort) {
        this.hostedGames = new ArrayList<>();
        this.lobbyGames = new ArrayList<>();

        this.loggedPlayersInGame = new HashMap<>();
        this.playersInGameReference = new HashMap<>();

        this.clientConnections = new ArrayList<>();

        this.assignedConnections = new HashMap<>();

        this.gameController = new GameController();
        this.serverPort = serverPort;



        System.out.println(ConsoleColor.PURPLE_BOLD_BRIGHT + "ERYANTIS SERVER" + ConsoleColor.RESET);
    }

    private Server() {
        this(23154);
    }


    /**
     * Set up and run all the server components
     *
     * @throws IOException when setting up a serverSocket fails
     */
    public void run() throws IOException {
        ServerNetworkHandler serverNetworkHandler = new ServerNetworkHandler(serverPort);
        serverNetworkHandler.run();
    }

    public static void main(String[] args) {
        if(args.length == 0)
            singleton = new Server();
        else {
            singleton = new Server(Integer.parseInt(args[0]));
        }

        try {
            singleton.run();
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "An IO error occurred. Please check your network connection" + ConsoleColor.RESET);
        }
    }
}
