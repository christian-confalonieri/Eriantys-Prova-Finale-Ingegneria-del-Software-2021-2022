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

    /**
     * @author Christian Confalonieri
     */
    @Override
    public void power() {
        super.power();
        getGameHandler().getGame().conquerIsland(getGameHandler().getGame().getEffectHandler().getChosenIsland());
    }

}
