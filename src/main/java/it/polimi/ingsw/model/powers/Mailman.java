package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.model.GameHandler;

public class Mailman extends PowerCard {

    public Mailman(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.MAILMAN);
        setCost(1);
    }

    @Override
    public void power() {
        super.power();

        //TODO
    }

    @Override
    public void endPower() {
        super.endPower();

        //TODO
    }

}
