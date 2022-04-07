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
     * This method should be called after calling the method to move mother nature,
     * this way you just move it further by a number of places equal to the integer additionalMoves.
     *
     * @author Christian Confalonieri
     */
    @Override
    public void power() {
        super.power();

        int n = getGameHandler().getGame().getEffectHandler().getAdditionalMoves();
        MotherNature motherNature = getGameHandler().getGame().getMotherNature();

        motherNature.move(n);

        getGameHandler().getGame().getEffectHandler().setAdditionalMoves(0);
    }

}
