package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.model.GameHandler;

public class Minstrel extends PowerCard {

    public Minstrel(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.MINSTREL);
        setCost(1);
    }

    @Override
    public void power() {
        super.power();

        //TODO

        getGameHandler().getGame().getEffectHandler().setEffectActive(false);
    }

}
