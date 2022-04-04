package it.polimi.ingsw.model.game.rules;

import it.polimi.ingsw.exceptions.InvalidRulesException;

public class CloudsRules {
    public final int numberOfClouds;

    CloudsRules(int numberOfClouds) throws InvalidRulesException {
        if (numberOfClouds < 0) throw new InvalidRulesException();
        this.numberOfClouds = numberOfClouds;
    }
}
