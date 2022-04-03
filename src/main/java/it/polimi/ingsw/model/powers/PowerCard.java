package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.model.GameHandler;
import it.polimi.ingsw.model.powers.PowerType;

public abstract class PowerCard {

    private int cost;
    private PowerType type;

    public PowerCard(GameHandler gameHandler) {

    }

    public void power() { }

    public void endPower() { }

}
