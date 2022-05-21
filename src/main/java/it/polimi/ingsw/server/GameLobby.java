package it.polimi.ingsw.server;

import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.rules.GameRules;

import java.util.*;

/**
 * GameLobby represent a waiting room for players waiting to start a game.
 * It is created with an action specifying the gameRules of the game and the size of the lobby
 * Players can join and leave the lobby and when a lobby is full the game is started by the sever
 */
public class GameLobby {

    public List<PlayerLobby> getPlayersWaiting() { return playersWaiting; }

    public Wizard getWizard(String playerId) {
        return playersWaiting.stream().filter(p -> p.getPlayerId().equals(playerId)).findAny().get().getWizard();
    }

    public GameRules getGameRules() {
        return gameRules;
    }

    public String getGameLobbyId() {
        return gameLobbyId;
    }

    private final String gameLobbyId;

    private final List<PlayerLobby> playersWaiting;
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
    public boolean addPlayer(String playerId, Wizard wizard) {
        if (playersWaiting.stream().noneMatch(p -> p.getPlayerId().equals(playerId)) && playersWaiting.stream().noneMatch(p -> p.getWizard().equals(wizard))) {
            playersWaiting.add(new PlayerLobby(playerId, wizard));
            return true;
        }
        else return false;
    }

    /** {
     * Remove a player from the lobby if the player is present
     *
     * @param playerId the id of the player that exits the lobby
     */
    public void removePlayer(String playerId) {
        playersWaiting.stream().filter(playerLobby -> playerLobby.getPlayerId().equals(playerId)).findAny().ifPresent(playersWaiting::remove);
    }

    public boolean isPlayerWaiting(String playerId) {
        return playersWaiting.stream().anyMatch(pl -> pl.getPlayerId().equals(playerId));
    }

    public boolean isEmpty() {
        return playersWaiting.isEmpty();
    }

    public GameLobby(GameRules gameRules, int numberOfPlayers) {
        this.gameLobbyId = UUID.randomUUID().toString();
        this.gameRules = gameRules;
        this.numberOfPlayers = numberOfPlayers;
        this.playersWaiting = new ArrayList<>();
    }

    @Override
    public String toString() {
        return numberOfPlayers + "P\t" + waitingPlayersToString() + "\t";
    }

    public String toStringNoColors() {return numberOfPlayers + "P\t" + waitingPlayersToStringNoColor() + "\t"; }

    private String waitingPlayersToString() {
        String r = "";
        for (PlayerLobby p:
             playersWaiting) {
            r += getPlayerColorToString(p);
        }
        r += "\t";
        return r;
    }

    private String waitingPlayersToStringNoColor() {
        String r = "";
        for (PlayerLobby p: playersWaiting)
            r += getPlayerColorToStringNoColor(p);
        r += "\t";
        return r;
    }

    private String getPlayerColorToString(PlayerLobby p) {
        return switch(p.getWizard()) {
            case GREEN -> ConsoleColor.GREEN + "G" + ConsoleColor.RESET;
            case YELLOW -> ConsoleColor.YELLOW + "Y" + ConsoleColor.RESET;
            case PURPLE -> ConsoleColor.PURPLE + "P" + ConsoleColor.RESET;
            case BLUE -> ConsoleColor.BLUE + "B" + ConsoleColor.RESET;
        };
    }

    private String getPlayerColorToStringNoColor(PlayerLobby p) {
        return switch(p.getWizard()) {
            case GREEN -> "G";
            case YELLOW -> "Y";
            case PURPLE -> "P";
            case BLUE -> "B";
        };
    }
}
