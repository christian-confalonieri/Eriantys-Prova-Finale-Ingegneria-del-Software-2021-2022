package it.polimi.ingsw.server;

import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.game.GameCreator;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.ServerNetworkHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListSet;
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

    public void addNewGameLobby(GameLobby gameLobby) { synchronized (lobbyGames) { lobbyGames.add(gameLobby); } }

    /**
     * Starts a game from a fill lobby
     * @param gameLobby lobby
     * @throws InvalidNewGameException If the game starting failed
     */
    public void startGame(GameLobby gameLobby) throws InvalidNewGameException {
        synchronized (lobbyGames) {
            if (gameLobby.canStart()) {
                lobbyGames.remove(gameLobby);
                GameHandler newGame = GameCreator.createGame(gameLobby.getPlayersWaiting(), gameLobby.getGameRules());
                synchronized (newGame) {
                    addGame(newGame);

                    for (PlayerLobby playerLobby : gameLobby.getPlayersWaiting()) {
                        addPlayerInGame(playerLobby.getPlayerId(), newGame,
                                newGame.getGame().getPlayers().stream().filter(p -> p.getName().equals(playerLobby.getPlayerId())).findAny().get());
                    }
                }
            }
        }
    }

    public List<GameLobby> getAllGameLobbys() { return lobbyGames; }

    public GameLobby getGameLobby(String lobbyId) {
        synchronized (lobbyGames) {
            return lobbyGames.stream().filter(lb -> lb.getGameLobbyId().equals(lobbyId)).findAny().orElse(null);
        }
    }

    /**
     * Get the first free lobby with the number of player specified
     * @param numberOfPlayers the size of the lobby
     * @return A game lobby if available
     */
    public GameLobby getFirstFreeLobby(int numberOfPlayers) {
        synchronized (lobbyGames) {
            GameLobby lb = null;
            for(GameLobby gameLobby : Server.getInstance().getAllGameLobbys()) {
                if(gameLobby.getLobbySize() == numberOfPlayers) {
                    lb = gameLobby;
                    break;
                }
            }
            return lb;
        }
    }

    /**
     * Exit the lobby if the player is connected to one
     * @param playerId The player
     */
    public void exitLobbys(String playerId) {
        synchronized (lobbyGames) {
            List<GameLobby> lobbyInGame = lobbyGames.stream().filter(l -> l.isPlayerWaiting(playerId)).toList();
            for (GameLobby lb : lobbyInGame) {
                lb.removePlayer(playerId);
                if (lb.isEmpty()) {
                    lobbyGames.remove(lb);
                    System.out.println(ConsoleColor.YELLOW + "[" + lb.getGameLobbyId() + "] was empty. Lobby deleted" + ConsoleColor.RESET);
                } // Removes if the lobby has become empty
            }
        }
    }

    public boolean isWaitingInALobby(String playerId) {
        synchronized (lobbyGames) {
            return lobbyGames.stream().anyMatch(l -> l.isPlayerWaiting(playerId));
        }
    }

    public ClientNetworkHandler getClientNetHandler(String playerId) {
        synchronized (assignedConnections) {
            return assignedConnections.get(playerId);
        }
    }

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

    /**
     * Bind a player id to a client connections
     * @param playerId The player name
     * @param networkHandler The client connection
     */
    public void assignConnection(String playerId, ClientNetworkHandler networkHandler) {
        synchronized (assignedConnections) { assignedConnections.put(playerId, networkHandler); }
    }

    /**
     * Unassign a player name from a client net handler
     * @param playerId the player name
     */
    public void unassignConnection(String playerId) { synchronized (assignedConnections) { assignedConnections.remove(playerId); } }

    /**
     * @param playerId The player name
     * @return true if assigned to a client net handler
     */
    public boolean isAssigned(String playerId) { synchronized (assignedConnections) { return assignedConnections.containsKey(playerId); } }

    /**
     * @param clientNetworkHandler Client network handler
     * @return true if its assigned to a player id
     */
    public boolean isAssigned(ClientNetworkHandler clientNetworkHandler) { synchronized (assignedConnections) { return assignedConnections.containsValue(clientNetworkHandler); } }

    /**
     * Get the player name associated with the client net handler
     * @param clientNetworkHandler The client net handler
     * @return The player name
     */
    public String getAssignedPlayerId(ClientNetworkHandler clientNetworkHandler) {
        synchronized (assignedConnections) { return assignedConnections.keySet().stream().filter(id -> assignedConnections.get(id).equals(clientNetworkHandler)).findAny().orElse(null); }
    }


    /**
     * Get the gameHandler in which the player is playing
     * @param playerId The player name
     * @return The gameHandler
     */
    public GameHandler getGameHandler(String playerId) {
        synchronized (loggedPlayersInGame) {
            return loggedPlayersInGame.get(playerId);
        }
    }

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

    /**
     * Assign a player to a game
     * @param playerId The player name
     * @param game The GameHandler
     * @param player The player Object
     */
    public void addPlayerInGame(String playerId, GameHandler game, Player player) {
        synchronized (loggedPlayersInGame) {
            loggedPlayersInGame.put(playerId, game);
            playersInGameReference.put(playerId, player);
        }
    }

    /**
     * Remove a player from the game its connected to (if any)
     * @param playerId The player name
     */
    public void removePlayerFromGame(String playerId) {
        synchronized (loggedPlayersInGame) {
            loggedPlayersInGame.remove(playerId);
            playersInGameReference.remove(playerId);
        }
    }

    /**
     * Checks if a player is connected to the server and connected to a game
     * @param playerId The player name
     * @return boolean
     */
    public boolean isInGame(String playerId) {
        synchronized (loggedPlayersInGame) {
            return loggedPlayersInGame.containsKey(playerId);
        }
    }

    /**
     * Get the player object stored in the server from its login name
     * @param playerId The player id
     * @return The player object
     */
    public Player getInGamePlayer(String playerId) {
        synchronized (loggedPlayersInGame) {
            return playersInGameReference.get(playerId);
        }
    }

    /**
     * Get all the players logged in a game
     * @param gameHandler The game
     * @return The list of players connected
     */
    private List<String> getLoggedPlayersInGame(GameHandler gameHandler) {
        synchronized (loggedPlayersInGame) {
            return loggedPlayersInGame.keySet().stream().filter(id -> loggedPlayersInGame.get(id).equals(gameHandler)).toList();
        }
    }

    /**
     * Returns all the client network handler associated with a gameHandler
     * @param gameHandler The game
     * @return The list of the sockt handlers
     */
    public List<ClientNetworkHandler> getConnectionsForGameBroadcast(GameHandler gameHandler) {
        synchronized (clientConnections) {
            List<String> playerIds = getLoggedPlayersInGame(gameHandler);
            List<ClientNetworkHandler> netConnections = playerIds.stream().map(this::getClientNetHandler).toList();
            return netConnections;
        }
    }

    public void addGame(GameHandler gameHandler) { synchronized (hostedGames) { hostedGames.add(gameHandler); } }
    public void removeGame(GameHandler gameHandler) { synchronized (hostedGames) { hostedGames.remove(gameHandler); } }

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

    /**
     * Starts the server application
     * @param args The application arguments
     */
    public static void main(String[] args) {
        if(args.length == 0)
            singleton = new Server();
        else {
            try { singleton = new Server(Integer.parseInt(args[0])); }
            catch (NumberFormatException e) { System.out.println(ConsoleColor.RED + "Could not convert arg to a port number" + ConsoleColor.RESET); return;}
        }

        try {
            singleton.run();
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED + "An IO error occurred. \nPlease check your network connection\nTry looking of another instance of this server application running\nTry verifying your port number if chosen\n" + ConsoleColor.RESET);
        }
    }
}
