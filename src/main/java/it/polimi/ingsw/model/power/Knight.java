package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;

/**
 * EFFECT: During the influence calculation this turn, you count as having 2 more influence.
 *
 * @author Christian Confalonieri
 */
public class Knight extends PowerCard {

    /**
     * In the constructor the character type and its usage cost is set.
     *
     * @param gameHandler
     * @author Christian Confalonieri
     */
    public Knight(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.KNIGHT);
        setCost(2);
    }

    public Knight() {
        super();
    }

    /**
     * This method only sets the value of additionInfluence to 2,
     * checking this value and calculating the influence is handled by the methods of the Island class.
     *
     * @author Christian Confalonieri
     */
    @Override
    public void power() {

        super.power();
        getGameHandler().getGame().getEffectHandler().setAdditionalInfluence(2);

    }

    /**
     * This method only sets the value of additionInfluence to 0
     *
     * @author Christian Confalonieri
     */
    @Override
    public void endPower() {

        super.endPower();
        getGameHandler().getGame().getEffectHandler().setAdditionalInfluence(0);

    }

}
