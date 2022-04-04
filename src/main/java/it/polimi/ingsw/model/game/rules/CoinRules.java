package it.polimi.ingsw.model.game.rules;

import it.polimi.ingsw.exceptions.InvalidRulesException;

public class CoinRules {
    public final int startingCoinsPerPlayer;
    public final int startingBoardCoins;

    protected void validate() throws InvalidRulesException {
        if (startingCoinsPerPlayer < 0) throw new InvalidRulesException();
        if (startingBoardCoins < 0) throw new InvalidRulesException();
    }

    protected CoinRules(int startingCoinsPerPlayer, int startingBoardCoins) {
        this.startingCoinsPerPlayer = startingCoinsPerPlayer;
        this.startingBoardCoins = startingBoardCoins;
    }
}
