package it.polimi.ingsw.model.game.rules;

import it.polimi.ingsw.exceptions.InvalidRulesException;

public class TowersRules {
    public final int numberOfTowers;

    TowersRules(int numberOfTowers) throws InvalidRulesException {
        if (numberOfTowers <= 0) throw new InvalidRulesException();
        this.numberOfTowers = numberOfTowers;
    }
}
