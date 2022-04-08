package it.polimi.ingsw.server;

import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameHandler;

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

    private Server() {}

    private List<GameHandler> hostedGames;
    private Map<String, GameHandler> loggedPlayersInGame; // Refers to the game in which the player is logged in
    private Map<String, Player> playersInGameReference; // The player is logged in as Player@...

    public List<GameHandler> getHostedGames() {
        return hostedGames;
    }

    public GameHandler getGameHandler(String playerId) {
        return loggedPlayersInGame.get(playerId);
    }
    public Player getPlayerReference(String playerId) { return playersInGameReference.get(playerId); }

}
