package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;

public class Herald extends PowerCard {

    /**
     * @author Christian Confalonieri
     */
    public Herald(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.HERALD);
        setCost(3);
    }

    @Override
    public void power() {
        super.power();

        //TODO

        getGameHandler().getGame().getEffectHandler().setEffectActive(false);
    }

}
