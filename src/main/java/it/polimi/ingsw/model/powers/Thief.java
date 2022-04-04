package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.model.GameHandler;

public class Thief extends PowerCard {

    public Thief(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.THIEF);
        setCost(3);
    }

    @Override
    public void power() {
        super.power();

        //TODO

        getGameHandler().getGame().getEffectHandler().setEffectActive(false);
    }

}
