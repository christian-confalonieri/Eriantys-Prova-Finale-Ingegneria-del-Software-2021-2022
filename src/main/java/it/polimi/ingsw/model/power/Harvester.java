package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.enumeration.PawnColor;

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
    public void power() {
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
