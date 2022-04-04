package it.polimi.ingsw.model.game.rules;

import it.polimi.ingsw.exceptions.InvalidRulesException;

public class PlayerRules {
    public final int startingPlayer;

    protected void validate() throws InvalidRulesException {
        if (startingPlayer < 0) throw new InvalidRulesException();
    }

    protected PlayerRules(int startingPlayer) {
        this.startingPlayer = startingPlayer;
    }
}
