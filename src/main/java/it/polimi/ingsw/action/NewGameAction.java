package it.polimi.ingsw.action;

import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.rules.GameRules;

public class NewGameAction extends MenuAction {
    private int numberOfPlayers;
    private GameRules gameRules;

    private String playerName; // Join as
    private Wizard wizard;

    public NewGameAction(ActionType actionType, String playerId, int numberOfPlayers, GameRules gameRules, String playerName, Wizard wizard) {
        super(actionType, playerId);
        this.gameRules = gameRules;
        this.numberOfPlayers = numberOfPlayers;
        this.playerName = playerName;
        this.wizard = wizard;
    }
}
