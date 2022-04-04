package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.model.GameHandler;
import it.polimi.ingsw.model.PawnColor;

/**
 * @author Christian Confalonieri
 */
public class Harvester extends PowerCard {

    PawnColor color;

    /**
     * @author Christian Confalonieri
     */
    public Harvester(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.HARVESTER);
        setCost(3);
    }

    @Override
    public void power() throws EmptyBagException {
        super.power();
    }

    /**
     * @author Christian Confalonieri
     */
    @Override
    public void endPower() {
        super.endPower();
        getGameHandler().getGame().getEffectHandler().setHarvesterColor(null);
    }

}
