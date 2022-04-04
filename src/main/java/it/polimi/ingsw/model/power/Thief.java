package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;

public class Thief extends PowerCard {

    /**
     * @author Christian Confalonieri
     */
    public Thief(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.THIEF);
        setCost(3);
    }

    @Override
    public void power() {
        super.power();

        //TODO
    }

}
