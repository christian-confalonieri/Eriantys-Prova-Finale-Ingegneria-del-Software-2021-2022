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

    synchronized public List<PlayerLobby> getPlayersWaiting() { return new ArrayList<>(playersWaiting); }

    public Wizard getWizard(String playerId) {
        return playersWaiting.stream().filter(p -> p.getPlayerId().equals(playerId)).findAny().get().getWizard();
    }

    synchronized public GameRules getGameRules() {
        return gameRules;
    }

    synchronized public String getGameLobbyId() {
        return gameLobbyId;
    }

    private final String gameLobbyId;

    private final List<PlayerLobby> playersWaiting;
    private final GameRules gameRules;
    private final int numberOfPlayers;

    synchronized public boolean canStart() {
        return playersWaiting.size() == numberOfPlayers;
    }

    synchronized public int getWaitingPlayersNumber() {
        return playersWaiting.size();
    }

    synchronized public int getLobbySize() {
        return numberOfPlayers;
    }

    /**
     * Add a player to the current lobby
     * The player can't be added if tne player is already in the lobby
     * or the name is already taken and the wizard is already taken
     * @return true if the player was added.
     */
    synchronized public boolean addPlayer(String playerId, Wizard wizard) {
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
    synchronized public void removePlayer(String playerId) {
        playersWaiting.stream().filter(playerLobby -> playerLobby.getPlayerId().equals(playerId)).findAny().ifPresent(playersWaiting::remove);
    }

    synchronized public boolean isPlayerWaiting(String playerId) {
        return playersWaiting.stream().anyMatch(pl -> pl.getPlayerId().equals(playerId));
    }

    synchronized public boolean isEmpty() {
        return playersWaiting.isEmpty();
    }

    public GameLobby(GameRules gameRules, int numberOfPlayers) {
        this.gameLobbyId = UUID.randomUUID().toString();
        this.gameRules = gameRules;
        this.numberOfPlayers = numberOfPlayers;
        this.playersWaiting = new ArrayList<>();
    }

    @Override
    synchronized public String toString() {
        return numberOfPlayers + "P\t" + waitingPlayersToString() + "\t";
    }

    synchronized public String toStringNoColors() {return numberOfPlayers + "P\t" + waitingPlayersToStringNoColor() + "\t"; }

    /**
     * @author Christian Confalonieri
     */
    synchronized public String[] toStringArrayPlayers()  {
        int size = getWaitingPlayersNumber();
        String[] playersLobby = new String[size];
        for(int i=0;i<size;i++) {
            playersLobby[i] = playersWaiting.get(i).getPlayerId() + "\t\t" + playersWaiting.get(i).getWizard().toString();
        }
        return playersLobby;
    }

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
