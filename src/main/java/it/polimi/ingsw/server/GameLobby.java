package it.polimi.ingsw.server;

import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.rules.GameRules;

import java.util.*;

public class GameLobby {
    public List<String> getPlayersWaiting() {
        return playersWaiting;
    }

    public String getInGameName(String playerId) {
        return names.get(playerId);
    }

    public Wizard getWizard(String playerId) {
        return wizards.get(playerId);
    }

    public GameRules getGameRules() {
        return gameRules;
    }

    public String getGameLobbyId() {
        return gameLobbyId;
    }

    private final String gameLobbyId;
    private final List<String> playersWaiting;
    private final Map<String, String> names;
    private final Map<String, Wizard> wizards;
    private final GameRules gameRules;
    private final int numberOfPlayers;

    public boolean canStart() {
        return playersWaiting.size() == numberOfPlayers;
    }

    public int getWaitingPlayersNumber() {
        return playersWaiting.size();
    }

    public int getLobbySize() {
        return numberOfPlayers;
    }

    /**
     * Add a player to the current lobby
     * The player can't be added if tne player is already in the lobby
     * or the name is already taken and the wizard is already taken
     * @return true if the player was added.
     */
    public boolean addPlayer(String playerId, String name, Wizard wizard) {
        if (!playersWaiting.contains(playerId) && !names.containsValue(name) && !wizards.containsValue(wizard)) {
            playersWaiting.add(playerId);
            names.put(playerId, name);
            wizards.put(playerId, wizard);
            return true;
        }
        return false;
    }

    /** {
     * Remove a player from the lobby if the player is present
     *
     * @param playerId the id of the player that exits the lobby
     */
    public void removePlayer(String playerId) {
        playersWaiting.remove(playerId);
        names.remove(playerId);
        wizards.remove(playerId);
    }

    public boolean isPlayerWaiting(String playerId) {
        return playersWaiting.contains(playerId);
    }

    public boolean isEmpty() {
        return playersWaiting.isEmpty();
    }

    public GameLobby(GameRules gameRules, int numberOfPlayers) {
        this.gameLobbyId = UUID.randomUUID().toString();
        this.gameRules = gameRules;
        this.numberOfPlayers = numberOfPlayers;
        this.playersWaiting = new ArrayList<>();
        this.names = new HashMap<>();
        this.wizards = new HashMap<>();
    }
}
