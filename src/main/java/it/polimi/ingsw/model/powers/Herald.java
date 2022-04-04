package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.model.GameHandler;

public class Herald extends PowerCard {

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
