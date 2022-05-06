package it.polimi.ingsw.action;

import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.server.GameLobby;

public class JoinGameAction extends MenuAction{

    public Wizard getWizard() {
        return wizard;
    }

    private GameLobby newGameLobby;

    public GameLobby getNewGameLobby() {
        return newGameLobby;
    }

    public void setNewGameLobby(GameLobby newGameLobby) {
        this.newGameLobby = newGameLobby;
    }

    private final int numberOfPlayers;
    private final Wizard wizard;

    public JoinGameAction(String playerId, int numberOfPlayers, Wizard wizard) {
        super(ActionType.JOINGAME, playerId);
        this.wizard = wizard;
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
