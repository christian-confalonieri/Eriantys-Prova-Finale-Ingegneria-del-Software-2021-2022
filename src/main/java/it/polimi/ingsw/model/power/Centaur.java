package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;

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
    public void power() {
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
