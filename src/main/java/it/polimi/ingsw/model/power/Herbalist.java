package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;

/**
 * In Setup, put the 4 No Entry tiles on this card.
 * EFFECT: Place a No Entry tile on an Island of your choice.
 * The first time Mother Nature ends her movement there,
 * put the No Entry tile back onto this card DO NOT calculate influence on that Island, or place any Towers.
 *
 * @author Christian Confalonieri
 */
public class Herbalist extends PowerCard {

    private int noEntryCards;

    /**
     * In the constructor the character type and its usage cost is set.
     * The integer noEntryCards is also set to 4.
     *
     * @param gameHandler
     * @author Christian Confalonieri
     */
    public Herbalist(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.HERBALIST);
        setCost(2);

        noEntryCards = 4;
    }

    /**
     * This method simply changes the noEntryCards boolean on the island to true,
     * checking the boolean and calculating the influence is handled by the methods on the island.
     * It also decrements the integer relative to the number of noEntryCards on this card.
     *
     * @author Christian Confalonieri
     */
    @Override
    public void power() {
        super.power();

        getGameHandler().getGame().getEffectHandler().getChosenIsland().setNoEntry(true);
        noEntryCards--;

    }

}
