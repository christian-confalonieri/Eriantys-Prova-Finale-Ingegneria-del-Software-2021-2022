package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;

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
    public void power() {
        super.power();

        getGameHandler().getGame().getEffectHandler().getChosenIsland().setNoEntry(true);
        noEntryCards--;

    }

}
