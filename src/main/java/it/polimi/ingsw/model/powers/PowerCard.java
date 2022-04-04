package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.model.GameHandler;

public abstract class PowerCard {

    private int cost;
    private PowerType type;
    private GameHandler gameHandler;

    public PowerCard(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public void power() throws EmptyBagException {

        gameHandler.getGame().getEffectHandler().setEffectActive(true);
        cost++;

    }

    public void endPower() {

        gameHandler.getGame().getEffectHandler().setEffectActive(false);

    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public PowerType getType() {
        return type;
    }

    public void setType(PowerType type) {
        this.type = type;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }
}
