package it.polimi.ingsw.server;

import it.polimi.ingsw.action.Action;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.game.GameCreator;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.ServerNetworkHandler;

import java.io.Console;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.Consumer;

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
            GameHandler newGame = GameCreator.createGame(gameLobby.getPlayersWaiting(), gameLobby.getGameRules());
            addGame(newGame);

            for(PlayerLobby playerLobby : gameLobby.getPlayersWaiting()) {
                addPlayerInGame(playerLobby.getPlayerId(), newGame,
                        newGame.getGame().getPlayers().stream().filter(p -> p.getName().equals(playerLobby.getPlayerId())).findAny().get());
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
            if (lb.isEmpty()) {
                lobbyGames.remove(lb);
                System.out.println(ConsoleColor.YELLOW + "[" + lb.getGameLobbyId() + "] was empty. Lobby deleted" + ConsoleColor.RESET);
            } // Removes if the lobby has become empty
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
        synchronized (clientConnections) {
            clientConnections.add(clientConnection);
        }
    }
    public void removeClientConnection(ClientNetworkHandler clientConnection) {
        synchronized (clientConnections)
        {
            clientConnections.remove(clientConnection);
        }
    }

    public void assignConnection(String playerId, ClientNetworkHandler networkHandler) {
        assignedConnections.put(playerId, networkHandler);
    }
    public void unassignConnection(String playerId) { assignedConnections.remove(playerId); }
    public boolean isAssigned(String playerId) { return assignedConnections.containsKey(playerId); }
    public boolean isAssigned(ClientNetworkHandler clientNetworkHandler) { return assignedConnections.containsValue(clientNetworkHandler); }
    public String getAssignedPlayerId(ClientNetworkHandler clientNetworkHandler) {
        return assignedConnections.keySet().stream().filter(id -> assignedConnections.get(id).equals(clientNetworkHandler)).findAny().orElse(null);
    }

    // public List<ClientNetworkHandler> getAllClientConnections() { return clientConnections; }
    public void forEachClientConnection(Consumer<ClientNetworkHandler> function) {
        synchronized (clientConnections) {
            clientConnections.forEach(function);
        }
    }

    public int getClientConnectionsSize() {
        synchronized (clientConnections) {
            return clientConnections.size();
        }
    }

    public void addPlayerInGame(String playerId, GameHandler game, Player player) {
        synchronized (loggedPlayersInGame) {
            loggedPlayersInGame.put(playerId, game);
            playersInGameReference.put(playerId, player);
        }
    }
    public void removePlayerFromGame(String playerId) {
        synchronized (loggedPlayersInGame) {
            loggedPlayersInGame.remove(playerId);
            playersInGameReference.remove(playerId);
        }
    }
    public boolean isInGame(String playerId) {
        return loggedPlayersInGame.containsKey(playerId);
    }

    public List<ClientNetworkHandler> getConnectionsForGameBroadcast(GameHandler gameHandler) {
        synchronized (clientConnections) {
            List<String> playerIds = loggedPlayersInGame.keySet().stream().filter(id -> loggedPlayersInGame.get(id).equals(gameHandler)).toList();
            List<ClientNetworkHandler> netConnections = playerIds.stream().map(this::getClientNetHandler).toList();
            return netConnections;
        }
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
            logInit("D:\\debug\\server");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(ConsoleColor.RED + "Could not initialize log file" + ConsoleColor.RESET);
            return;
        }

        try {
            singleton.run();
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "An IO error occurred. Please check your network connection" + ConsoleColor.RESET);
        }
    }




    public static void logActionReceived(ClientNetworkHandler networkHandler, Action action) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            logFileWriter.write(timestamp + ":\t[RECEIVED]\t");
            logFileWriter.write(networkHandler.toString() + " " + action.getActionType() +  "\t\tplayerId:{" +  action.getPlayerId() + "}\n");

            logFileWriter.flush();
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "LOG WRITING FAILED\n");
            e.printStackTrace();
            System.out.println(ConsoleColor.RESET);
        }
    }

    public static void logActionIgnored(ClientNetworkHandler networkHandler, Action action) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            logFileWriter.write(timestamp + ":\t(IGNORED)\t");
            logFileWriter.write(networkHandler.toString() + " " + action.getActionType() +  "\t\tplayerId:{" +  action.getPlayerId() + "}\n");

            logFileWriter.flush();
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "LOG WRITING FAILED\n");
            e.printStackTrace();
            System.out.println(ConsoleColor.RESET);
        }
    }

    public static void logActionSent(ClientNetworkHandler networkHandler, Action action) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            logFileWriter.write(timestamp + ":\t[SENT]   \t");
            logFileWriter.write(networkHandler.toString() + " " + action.getActionType() +  "\t\tplayerId:{" +  action.getPlayerId() + "}\n");

            logFileWriter.flush();
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "LOG WRITING FAILED\n");
            e.printStackTrace();
            System.out.println(ConsoleColor.RESET);
        }
    }


    public static void logControllerCall() {

    }

    private static FileWriter logFileWriter;

    public static void logInit(String logDirectory) throws IOException {
        String runName = UUID.randomUUID().toString();
        String filePath = logDirectory + "\\ServerLog_" + runName + ".txt";

        System.out.println(ConsoleColor.GREEN_BOLD + "DEBUG LOG FILE: " + filePath + ConsoleColor.RESET);

        logFileWriter = new FileWriter(filePath);
        logFileWriter.write("RUN " + runName + "\n");
        logFileWriter.write(new Timestamp(System.currentTimeMillis()) + " server booted up\n\n");
        logFileWriter.flush();
    }
}
