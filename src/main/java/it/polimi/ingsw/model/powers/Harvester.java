package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.model.GameHandler;
import it.polimi.ingsw.model.PawnColor;

public class Harvester extends PowerCard {

    PawnColor color;

    public Harvester(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.HARVESTER);
        setCost(3);
    }

    @Override
    public void power() {
        super.power();
    }

    @Override
    public void endPower() {
        super.endPower();
        getGameHandler().getGame().getEffectHandler().setHarvesterColor(null);
    }

}
