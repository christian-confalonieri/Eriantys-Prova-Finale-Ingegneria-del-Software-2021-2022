package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;

/**
 * EFFECT: When resolving a Conquering on an Island, Towers do not count towards influence.
 *
 * @author Christian Confalonieri
 */
public class Centaur extends PowerCard {

    /**
     * In the constructor the character type and its usage cost is set.
     *
     * @param gameHandler
     * @author Christian Confalonieri
     */
    public Centaur(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.CENTAUR);
        setCost(3);
    }

    /**
     * Simply change the value of the boolean skipTowers to true,
     * checking that value and calculating the influence will be handled by the methods in the Island class.
     *
     * @author Christian Confalonieri
     */
    @Override
    public void power() {
        super.power();
        getGameHandler().getGame().getEffectHandler().setSkipTowers(true);
    }

    /**
     * Simply change the value of the boolean skipTowers to false
     *
     * @author Christian Confalonieri
     */
    @Override
    public void endPower() {
        super.endPower();
        getGameHandler().getGame().getEffectHandler().setSkipTowers(false);
    }

}
