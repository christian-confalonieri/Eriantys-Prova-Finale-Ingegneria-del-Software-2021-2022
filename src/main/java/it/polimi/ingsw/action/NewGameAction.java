package it.polimi.ingsw.action;

import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.rules.GameRules;

public class NewGameAction extends MenuAction {
    private int numberOfPlayers;
    private GameRules gameRules;

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public GameRules getGameRules() {
        return gameRules;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Wizard getWizard() {
        return wizard;
    }

    private String playerName; // Join as
    private Wizard wizard;

    public NewGameAction(String playerId, int numberOfPlayers, GameRules gameRules, String playerName, Wizard wizard) {
        super(ActionType.NEWGAME, playerId);
        this.gameRules = gameRules;
        this.numberOfPlayers = numberOfPlayers;
        this.playerName = playerName;
        this.wizard = wizard;
    }
}
