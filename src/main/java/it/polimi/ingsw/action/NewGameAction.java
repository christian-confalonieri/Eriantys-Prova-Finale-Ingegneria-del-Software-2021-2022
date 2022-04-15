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

    public Wizard getWizard() {
        return wizard;
    }

    private Wizard wizard;

    public NewGameAction(String playerId, int numberOfPlayers, GameRules gameRules, Wizard wizard) {
        super(ActionType.NEWGAME, playerId);
        this.gameRules = gameRules;
        this.numberOfPlayers = numberOfPlayers;
        this.wizard = wizard;
    }
}
