package it.polimi.ingsw.model.game.rules;

import it.polimi.ingsw.exceptions.InvalidRulesException;

public class TowersRules {
    public final int numberOfTowers;

    protected void validate() throws InvalidRulesException {
        if (numberOfTowers <= 0) throw new InvalidRulesException();
    }

    protected TowersRules(int numberOfTowers) throws InvalidRulesException {
        this.numberOfTowers = numberOfTowers;
    }
}
