package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;

/**
 * @author Christian Confalonieri
 */
public class Knight extends PowerCard {

    /**
     * @author Christian Confalonieri
     */
    public Knight(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.KNIGHT);
        setCost(2);
    }

    /**
     * @author Christian Confalonieri
     */
    @Override
    public void power() {

        super.power();
        getGameHandler().getGame().getEffectHandler().setAdditionalInfluence(2);

    }

    /**
     * @author Christian Confalonieri
     */
    @Override
    public void endPower() {

        super.endPower();
        getGameHandler().getGame().getEffectHandler().setAdditionalInfluence(0);

    }

}
