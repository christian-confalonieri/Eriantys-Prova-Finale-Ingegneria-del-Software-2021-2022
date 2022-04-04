package it.polimi.ingsw.model.game.rules;

import it.polimi.ingsw.exceptions.InvalidRulesException;

public class IslandsRules {
    public final int numberOfIslands;

    public IslandsRules(int numberOfIslands) throws InvalidRulesException {
        if (numberOfIslands <= 3) throw new InvalidRulesException();
        this.numberOfIslands = numberOfIslands;
    }
}
