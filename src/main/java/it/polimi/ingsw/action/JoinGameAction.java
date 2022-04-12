package it.polimi.ingsw.action;

import it.polimi.ingsw.model.enumeration.Wizard;

public class JoinGameAction extends MenuAction{
    public String getGameLobbyId() {
        return gameLobbyId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Wizard getWizard() {
        return wizard;
    }

    private final String gameLobbyId;
    private final String playerName; // Join as
    private final Wizard wizard;

    public JoinGameAction(String playerId, String gameLobbyId, String playerName, Wizard wizard) {
        super(ActionType.JOINGAME, playerId);
        this.playerName = playerName;
        this.wizard = wizard;
        this.gameLobbyId = gameLobbyId;
    }
}
