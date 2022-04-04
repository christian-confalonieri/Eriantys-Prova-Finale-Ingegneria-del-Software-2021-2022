package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.model.GameHandler;

public class Knight extends PowerCard {

    public Knight(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.KNIGHT);
        setCost(2);
    }

    @Override
    public void power() throws EmptyBagException {

        super.power();
        getGameHandler().getGame().getEffectHandler().setAdditionalInfluence(2);

    }

    @Override
    public void endPower() {

        super.endPower();
        getGameHandler().getGame().getEffectHandler().setAdditionalInfluence(0);

    }

}
