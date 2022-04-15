package it.polimi.ingsw.server;

import it.polimi.ingsw.model.enumeration.Wizard;

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
