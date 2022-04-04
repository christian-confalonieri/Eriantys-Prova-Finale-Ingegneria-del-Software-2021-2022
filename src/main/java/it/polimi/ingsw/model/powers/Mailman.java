package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.model.GameHandler;

public class Mailman extends PowerCard {

    /**
     * @author Christian Confalonieri
     */
    public Mailman(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.MAILMAN);
        setCost(1);
    }

    @Override
    public void power() throws EmptyBagException {
        super.power();

        //TODO
    }

    @Override
    public void endPower() {
        super.endPower();

        //TODO
    }

}
