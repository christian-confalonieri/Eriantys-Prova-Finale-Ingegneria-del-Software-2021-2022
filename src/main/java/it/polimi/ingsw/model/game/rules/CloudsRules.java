package it.polimi.ingsw.model.game.rules;

import it.polimi.ingsw.exceptions.InvalidRulesException;

public class CloudsRules {
    public final int numberOfClouds;

    protected void validate() throws InvalidRulesException {
        if (numberOfClouds < 0) throw new InvalidRulesException();
    }

    protected CloudsRules(int numberOfClouds) {
        this.numberOfClouds = numberOfClouds;
    }
}
