package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;

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

    public Harvester() {
        super();
    }

    /**
     * Simply change the value of the harvesterColor to null and set the value of activeHarvester to false
     *
     * @author Christian Confalonieri
     */
    @Override
    public void endPower() {
        super.endPower();
        getGameHandler().getGame().getEffectHandler().setHarvesterColor(null);
    }

}
