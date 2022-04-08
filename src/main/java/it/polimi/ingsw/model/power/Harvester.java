package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.enumeration.PawnColor;

/**
 * EFFECT: Choose a color of Student: during the influence calculation this turn, that color adds no influence.
 *
 * @author Christian Confalonieri
 */
public class Harvester extends PowerCard {

    /**
     * In the constructor the character type and its usage cost is set.
     *
     * @param gameHandler
     * @author Christian Confalonieri
     */
    public Harvester(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.HARVESTER);
        setCost(3);
    }

    /**
     * This method does nothing besides increasing the cost of the card
     * since the control of the chosen color and the calculation of the influence is handled by the methods of the Island class.
     *
     * @author Christian Confalonieri
     */
    @Override
    public void power() {
        super.power();
        getGameHandler().getGame().getEffectHandler().setActiveHarvester(true);
    }

    /**
     * Simply change the value of the harvesterColor to null
     *
     * @author Christian Confalonieri
     */
    @Override
    public void endPower() {
        super.endPower();
        getGameHandler().getGame().getEffectHandler().setHarvesterColor(null);
        getGameHandler().getGame().getEffectHandler().setActiveHarvester(false);
    }

}
