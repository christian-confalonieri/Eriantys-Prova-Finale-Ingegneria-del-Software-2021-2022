package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.entity.Island;
import it.polimi.ingsw.model.entity.MotherNature;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;

/**
 * EFFECT: You may move Mother Nature up to 2 additional Islands than is indicated by the Assistant card you’ve played.
 *
 * @author Christian Confalonieri
 */
public class Mailman extends PowerCard {

    /**
     * In the constructor the character type and its usage cost is set.
     *
     * @param gameHandler
     * @author Christian Confalonieri
     */
    public Mailman(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.MAILMAN);
        setCost(1);
    }

    /**
     * @author Christian Confalonieri
     */
    @Override
    public void power() {
        super.power();
        getGameHandler().getGame().getEffectHandler().setActiveMailman(true);
    }

    /**
     * @author Christian Confalonieri
     */
    @Override
    public void endPower() {
        super.endPower();
        getGameHandler().getGame().getEffectHandler().setActiveMailman(false);
        getGameHandler().getGame().getEffectHandler().setAdditionalMoves(0);
    }
}
