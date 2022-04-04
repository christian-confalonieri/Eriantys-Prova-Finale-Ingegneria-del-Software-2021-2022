package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.model.GameHandler;

/**
 * @author Christian Confalonieri
 */
public class Herbalist extends PowerCard {

    private int noEntryCards;

    /**
     * @author Christian Confalonieri
     */
    public Herbalist(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.HERBALIST);
        setCost(2);

        noEntryCards = 4;
    }

    /**
     * @author Christian Confalonieri
     */
    @Override
    public void power() throws EmptyBagException {
        super.power();

        getGameHandler().getGame().getEffectHandler().getChosenIsland().setNoEntry(true);
        noEntryCards--;

    }

    /**
     * @author Christian Confalonieri
     */
    @Override
    public void endPower() {
        super.endPower();

        getGameHandler().getGame().getEffectHandler().getChosenIsland().setNoEntry(false);
        noEntryCards++;
    }

}
