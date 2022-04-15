package it.polimi.ingsw.server;

import it.polimi.ingsw.model.enumeration.Wizard;

/**
 * PlayerLobby is a structure containing only the information of a player waiting to start a game
 * A list of playerLobby is then used by the model to get the players' information when creating a new game
 */
public class PlayerLobby {
    public String getPlayerId() {
        return playerId;
    }

    public Wizard getWizard() {
        return wizard;
    }

    private final String playerId;
    private final Wizard wizard;

    public PlayerLobby(String playerId, Wizard wizard) {
        this.playerId = playerId;
        this.wizard = wizard;
    }


}
