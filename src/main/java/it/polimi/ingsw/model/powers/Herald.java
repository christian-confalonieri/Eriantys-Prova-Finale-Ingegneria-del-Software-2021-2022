package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.model.GameHandler;

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
    public void power() throws EmptyBagException {
        super.power();

        //TODO

        getGameHandler().getGame().getEffectHandler().setEffectActive(false);
    }

}
