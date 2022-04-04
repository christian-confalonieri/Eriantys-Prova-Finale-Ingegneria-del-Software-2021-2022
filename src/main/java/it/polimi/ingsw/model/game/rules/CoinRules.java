package it.polimi.ingsw.model.game.rules;

import it.polimi.ingsw.exceptions.InvalidRulesException;

public class CoinRules {
    public final int startingCoinsPerPlayer;
    public final int startingBoardCoins;

    CoinRules(int startingCoinsPerPlayer, int startingBoardCoins) throws InvalidRulesException {
        if (startingCoinsPerPlayer < 0) throw new InvalidRulesException();
        this.startingCoinsPerPlayer = startingCoinsPerPlayer;
        if (startingBoardCoins < 0) throw new InvalidRulesException();
        this.startingBoardCoins = startingBoardCoins;
    }
}
