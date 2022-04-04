package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.model.GameHandler;

public class Centaur extends PowerCard {

    public Centaur(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.CENTAUR);
        setCost(3);
    }

    @Override
    public void power() {
        super.power();
        getGameHandler().getGame().getEffectHandler().setSkipTowers(true);
    }

    @Override
    public void endPower() {
        super.endPower();
        getGameHandler().getGame().getEffectHandler().setSkipTowers(false);
    }

}
