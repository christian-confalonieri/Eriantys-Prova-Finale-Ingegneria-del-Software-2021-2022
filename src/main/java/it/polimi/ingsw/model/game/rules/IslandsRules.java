package it.polimi.ingsw.model.game.rules;

import it.polimi.ingsw.exceptions.InvalidRulesException;

public class IslandsRules {
    public final int numberOfIslands;

    protected void validate() throws InvalidRulesException {
        if (numberOfIslands <= 3) throw new InvalidRulesException();
    }

    protected IslandsRules(int numberOfIslands) {
        this.numberOfIslands = numberOfIslands;
    }
}
