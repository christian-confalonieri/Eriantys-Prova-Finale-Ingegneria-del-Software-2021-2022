package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.model.GameHandler;

/**
 * @author Christian Confalonieri
 */
public class Centaur extends PowerCard {

    /**
     * @author Christian Confalonieri
     */
    public Centaur(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.CENTAUR);
        setCost(3);
    }

    /**
     * @author Christian Confalonieri
     */
    @Override
    public void power() throws EmptyBagException {
        super.power();
        getGameHandler().getGame().getEffectHandler().setSkipTowers(true);
    }

    /**
     * @author Christian Confalonieri
     */
    @Override
    public void endPower() {
        super.endPower();
        getGameHandler().getGame().getEffectHandler().setSkipTowers(false);
    }

}
