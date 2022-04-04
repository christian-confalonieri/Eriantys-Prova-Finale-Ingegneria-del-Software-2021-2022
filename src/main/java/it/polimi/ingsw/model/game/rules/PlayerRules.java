package it.polimi.ingsw.model.game.rules;

import it.polimi.ingsw.exceptions.InvalidRulesException;

public class PlayerRules {
    public final int startingPlayer;

    PlayerRules(int startingPlayer) throws InvalidRulesException {
        if (startingPlayer < 0) throw new InvalidRulesException();
        this.startingPlayer = startingPlayer;
    }
}
