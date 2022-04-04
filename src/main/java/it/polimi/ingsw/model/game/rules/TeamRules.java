package it.polimi.ingsw.model.game.rules;

import it.polimi.ingsw.exceptions.InvalidRulesException;

public class TeamRules {
    public final int[] teamOne;
    public final int[] teamTwo;

    protected void validate() throws InvalidRulesException {
        for (int p : teamOne) {
            if (p < 0) {
                throw new InvalidRulesException();
            }
        }
        for (int p : teamTwo) {
            if (p < 0) {
                throw new InvalidRulesException();
            }
        }
    }

    protected TeamRules(int[] teamOne, int[] teamTwo) {
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
    }
}
